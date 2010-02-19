package com.gemserk.componentsengine.templates;

import groovy.lang.Closure;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.entities.Root;
import com.gemserk.componentsengine.input.GroovyInputMappingBuilder;
import com.gemserk.componentsengine.messages.GenericMessage;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.scene.BuilderUtils;
import com.gemserk.componentsengine.scene.ComponentsHolderBuilder;
import com.gemserk.componentsengine.scene.MapBuilder;
import com.gemserk.componentsengine.scene.PropertiesHolderBuilder;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class GroovyEntityBuilder {

	protected TemplateProvider templateProvider;
	protected Entity entity;
	protected Injector injector;
	BuilderUtils utils;
	protected PropertiesHolderBuilder propertiesHolderBuilder;
	protected ComponentsHolderBuilder componentHolderBuilder;
	protected String defaultEntityName;
	protected Map<String, Object> parameters;
	GroovyInputMappingBuilder inputMappingBuilder;

	public GroovyEntityBuilder(String defaultEntityName, Map<String, Object> parameters) {
		this.defaultEntityName = defaultEntityName;
		this.parameters = parameters;
	}

	public GroovyEntityBuilder(Entity entity, Map<String, Object> parameters) {
		this.entity = entity;
		this.parameters = parameters;
		this.defaultEntityName = entity.getId();
	}

	@Inject
	public void setUtils(BuilderUtils utils) {
		this.utils = utils;
	}

	public void genericComponent(final Map<String, Object> parameters, final Closure closure) {
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
					if (!parameters.get("messageId").equals(genericMessage.getId()))
						return;

					closure.setDelegate(this);
					closure.call(genericMessage);
				}
			}
		});
	}

	public void property(String key, Object value) {
		propertiesHolderBuilder.property(key, value);
	}

	public void propertyRef(String key, String referencedPropertyName) {
		propertiesHolderBuilder.propertyRef(key, referencedPropertyName);
	}

	public void component(Component component, Closure closure) {
		componentHolderBuilder.component(component, closure);
	}

	public void component(Component component) {
		componentHolderBuilder.component(component);
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

	@Inject
	public void setInjector(Injector injector) {
		this.injector = injector;
	}

	@Inject
	public void setTemplateProvider(TemplateProvider templateProvider) {
		this.templateProvider = templateProvider;
	}

	public BuilderUtils getUtils() {
		return utils;
	}

	public void child(String templateName, String entityName, Map<String, Object> parameters) {
		Entity child = templateProvider.getTemplate(templateName).instantiate(entityName, parameters);
		entity.addEntity(child);
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

		child(templateName, entityName, mapBuilder.getParameters());
	}

	public Entity entity(Closure closure) {
		return this.entity(defaultEntityName, closure);
	}

	public Entity entity(String id, Closure closure) {
		if (entity == null)
			entity = new Entity(id);

		propertiesHolderBuilder = new PropertiesHolderBuilder(entity);
		componentHolderBuilder = new ComponentsHolderBuilder(entity, propertiesHolderBuilder, injector);

		closure.setDelegate(this);
		closure.call();
		return entity;
	}

	public void parent(String parent) {
		EntityTemplate parentTemplate = this.templateProvider.getTemplate(parent);
		parentTemplate.apply(entity, parameters);
	}

	@Inject
	public void setInputMappingBuilder(GroovyInputMappingBuilder inputMappingBuilder) {
		this.inputMappingBuilder = inputMappingBuilder;
	}

	public void input(String id, Closure closure) {
		Component inputcomponent = inputMappingBuilder.configure(id, closure);
		component(inputcomponent);
	}

	public void input(String id, String mapping) {
		Component inputcomponent = inputMappingBuilder.configure(id, mapping);
		component(inputcomponent);
	}

}