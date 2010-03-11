package com.gemserk.componentsengine.builders;

import groovy.lang.Closure;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.entities.Root;
import com.gemserk.componentsengine.input.GroovyInputMappingBuilder;
import com.gemserk.componentsengine.messages.GenericMessage;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.properties.ClosureProperty;
import com.gemserk.componentsengine.properties.ReferenceProperty;
import com.gemserk.componentsengine.properties.SimpleProperty;
import com.gemserk.componentsengine.templates.EntityTemplate;
import com.gemserk.componentsengine.templates.TemplateProvider;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.internal.Lists;

public class GroovyEntityBuilder {

	protected TemplateProvider templateProvider;
	protected Injector injector;
	BuilderUtils utils;

	protected String defaultEntityName;

	GroovyInputMappingBuilder inputMappingBuilder;

	public class BuilderContext {

		GroovyEntityBuilder builder;

		Entity entity;

		public BuilderContext(Entity entity, GroovyEntityBuilder builder) {
			this.entity = entity;
			this.builder = builder;
		}

		public void property(String key, Object value) {
			entity.addProperty(key, new SimpleProperty<Object>(value));
		}

		public void propertyRef(String key, String referencedPropertyName) {
			entity.addProperty(key, new ReferenceProperty<Object>(referencedPropertyName, entity));
		}

		public void property(String key, Closure closure) {
			entity.addProperty(key, new ClosureProperty(entity, closure));
		}

		@SuppressWarnings("unchecked")
		public void genericComponent(final Map<String, Object> parameters, final Closure closure) {
			Object messageIdsCandidates = parameters.get("messageId");
			final Collection messageIds;
			if (messageIdsCandidates == null)
				throw new RuntimeException("messageId cant be null");

			if (messageIdsCandidates instanceof Collection) {
				messageIds = (Collection) messageIdsCandidates;
			} else {
				messageIds = Lists.newArrayList(messageIdsCandidates.toString());
			}

			component(new Component((String) parameters.get("id")) {

				@Inject
				@Root
				Entity rootEntity;

				@Inject
				MessageQueue messageQueue;

				@Override
				public void handleMessage(Message message) {
					if (message instanceof GenericMessage) {

						GenericMessage genericMessage = (GenericMessage) message;

						if (!messageIds.contains(genericMessage.getId()))
							return;

						closure.setDelegate(this);
						closure.call(genericMessage);
					}
				}
			});
		}

		public void component(Component component) {
			injector.injectMembers(component);
			entity.addComponent(component);
		}

		public void component(final Component component, Closure closure) {
			component(component);

			closure.setDelegate(new Object() {

				@SuppressWarnings("unused")
				public void property(String key, Object value) {
					BuilderContext.this.property(component.getId() + "." + key, value);
				}

				@SuppressWarnings("unused")
				public void propertyRef(String key, String referencedPropertyName) {
					BuilderContext.this.propertyRef(component.getId() + "." + key, referencedPropertyName);
				}

				@SuppressWarnings("unused")
				public void property(String key, Closure closure) {
					BuilderContext.this.property(component.getId() + "." + key, closure);
				}

			});
			closure.setResolveStrategy(Closure.DELEGATE_FIRST);
			closure.call();
		}

		public Entity getEntity() {
			return entity;
		}

		public void tags(String... tags) {
			this.tags(Arrays.asList(tags));
		}

		public void tags(List<String> tags) {
			entity.getTags().addAll(tags);
		}

		public BuilderUtils getUtils() {
			return utils;
		}

		public void child(String templateName, String entityName, Map<String, Object> parameters) {
			Entity child = templateProvider.getTemplate(templateName).instantiate(entityName, parameters);
			child(child);
		}

		public void child(Map<String, Object> dslParameters) {
			String entityName = (String) dslParameters.get("id");
			String templateName = (String) dslParameters.get("template");
			Map<String, Object> parameters = (Map<String, Object>) dslParameters.get("parameters");
			child(templateName, entityName, parameters);
		}

		public void child(Map<String, Object> dslParameters, Closure closure) {
			String entityName = (String) dslParameters.get("id");
			String templateName = (String) dslParameters.get("template");

			MapBuilder mapBuilder = new MapBuilder();

			closure.setResolveStrategy(Closure.DELEGATE_FIRST);
			closure.setDelegate(mapBuilder);

			closure.call();

			child(templateName, entityName, mapBuilder.getInnerParameters());
		}

		public void child(Entity child) {
			entity.addEntity(child);
		}

		public Map<String, Object> map(Closure closure) {
			MapBuilder mapBuilder = new MapBuilder();

			closure.setResolveStrategy(Closure.DELEGATE_FIRST);
			closure.setDelegate(mapBuilder);

			closure.call();

			return mapBuilder.innerParameters;
		}

		public void parent(String parent) {
			this.parent(parent, new HashMap<String, Object>());
		}

		public void parent(String parent, Map<String, Object> parameters) {
			EntityTemplate parentTemplate = templateProvider.getTemplate(parent);
			parentTemplate.apply(entity, parameters);
		}

		public void parent(String parent, Closure closure) {
			EntityTemplate parentTemplate = templateProvider.getTemplate(parent);

			MapBuilder mapBuilder = new MapBuilder();

			closure.setResolveStrategy(Closure.DELEGATE_FIRST);
			closure.setDelegate(mapBuilder);

			closure.call();

			parentTemplate.apply(entity, mapBuilder.innerParameters);
		}

		public void input(String id, Closure closure) {
			Component inputcomponent = inputMappingBuilder.configure(id, closure);
			component(inputcomponent);
		}

		public void input(String id, String mapping) {
			Component inputcomponent = inputMappingBuilder.configure(id, mapping);
			component(inputcomponent);
		}

		// / calls to builder

		public Entity entity(Closure closure) {
			return builder.entity(closure);
		}

		public Entity entity(String id, Closure closure) {
			return builder.entity(id, closure);
		}

	}

	@Inject
	public void setUtils(BuilderUtils utils) {
		this.utils = utils;
	}

	@Inject
	public void setInjector(Injector injector) {
		this.injector = injector;
	}

	@Inject
	public void setTemplateProvider(TemplateProvider templateProvider) {
		this.templateProvider = templateProvider;
	}

	@Inject
	public void setInputMappingBuilder(GroovyInputMappingBuilder inputMappingBuilder) {
		this.inputMappingBuilder = inputMappingBuilder;
	}

	public GroovyEntityBuilder(String defaultEntityName) {
		this.defaultEntityName = defaultEntityName;
	}

	Entity entity = null;

	public GroovyEntityBuilder(Entity entity) {
		this.entity = entity;
	}

	public Entity entity(Closure closure) {
		return this.entity(defaultEntityName, closure);
	}

	public Entity entity(String id, Closure closure) {

		Entity entity = new Entity(id);

		if (this.entity != null)
			entity = this.entity;

		closure.setDelegate(new BuilderContext(entity, this));
		closure.setResolveStrategy(Closure.DELEGATE_FIRST);
		closure.call();

		return entity;
	}

}