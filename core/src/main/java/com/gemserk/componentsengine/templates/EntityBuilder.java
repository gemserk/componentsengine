package com.gemserk.componentsengine.templates;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.properties.Property;
import com.gemserk.componentsengine.properties.ReferenceProperty;
import com.gemserk.componentsengine.properties.SimpleProperty;
import com.google.inject.Inject;
import com.google.inject.Injector;

@SuppressWarnings("unchecked")
public abstract class EntityBuilder {

	protected Entity entity;

	protected Map<String, Object> parameters;

	@Inject
	private Injector injector;

	@Inject
	protected TemplateProvider templateProvider;

	public void tags(String... tags) {
		for (String tag : tags) {
			entity.getTags().add(tag);
		}
	}

	public String getId() {
		return "";
	}

	public void property(String key, Object value) {
		if (value instanceof Property)
			entity.addProperty(key, (Property) value);
		else
			entity.addProperty(key, new SimpleProperty<Object>(value));
	}

	public void property(String key, Object value, Object defaultValue) {
		if (value == null)
			property(key, defaultValue);
		else 
			property(key, value);
	}

	public void propertyRef(String key, String ref) {
		entity.addProperty(key, new ReferenceProperty<Object>(ref, entity));
	}

	public void child(Entity child) {
		entity.addEntity(child);
	}

	public ComponentPropertiesReceiver component(Component component) {
		injector.injectMembers(component);
		entity.addComponent(component);
		return new ComponentPropertiesReceiver(component.getId(), entity);
	}

	public abstract void build();

	void setEntity(Entity currentEntity) {
		entity = currentEntity;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	/**
	 * Applies a template to the current entity.
	 * 
	 * @param templateName
	 *            name of the template to apply.
	 */
	public void parent(String templateName) {
		EntityTemplate parentTemplate = templateProvider.getTemplate(templateName);
		parentTemplate.apply(entity);
	}

	/**
	 * Applies a template to the current entity.
	 * 
	 * @param templateName
	 *            name of the template to apply.
	 * @param parameters
	 *            paramteres to invoke the template.
	 */
	public void parent(String templateName, Map<String, Object> parameters) {
		EntityTemplate parentTemplate = templateProvider.getTemplate(templateName);
		parentTemplate.apply(entity, parameters);
	}

	public static class ComponentPropertiesReceiver {
		private final String componentId;
		private final Entity entity;

		public ComponentPropertiesReceiver(String id, Entity entity) {
			this.componentId = id;
			this.entity = entity;
		}

		public void withProperties(ComponentProperties componentProperties) {
			componentProperties.execute(entity, componentId);
		}
	}

	public static abstract class ComponentProperties {

		List<ExecutableWithEntityAndId> commands = new ArrayList();

		public abstract class ExecutableWithEntityAndId {
			public abstract void execute(Entity entity, String componentId);
		}

		public void property(final String key, final Property<Object> property) {
			commands.add(new ExecutableWithEntityAndId() {

				@Override
				public void execute(Entity entity, String componentId) {
					entity.addProperty(componentId + "." + key, property);

				}
			});
		}

		public void property(final String key, final Object value) {
			commands.add(new ExecutableWithEntityAndId() {

				@Override
				public void execute(Entity entity, String componentId) {
					entity.addProperty(componentId + "." + key, new SimpleProperty<Object>(value));

				}
			});
		}
		
		public void property(final String key, final Object value, Object defaultValue) {
			if (value == null)
				property(key, defaultValue);
			else
				property(key, value);
		}

		public void propertyRef(final String key, final String ref) {
			commands.add(new ExecutableWithEntityAndId() {

				@Override
				public void execute(Entity entity, String componentId) {
					entity.addProperty(componentId + "." + key, new ReferenceProperty<Object>(ref, entity));

				}
			});

		}

		public void propertyRef(final String key) {
			propertyRef(key, key);
		}

		private void execute(Entity entity, String componentId) {
			for (ExecutableWithEntityAndId command : commands) {
				command.execute(entity, componentId);
			}
		}

	}
}