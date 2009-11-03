package com.gemserk.componentsengine.properties;

import com.gemserk.componentsengine.entities.Entity;

@SuppressWarnings("unchecked")
public class PropertyLocator<T> {

	private String name;

	public PropertyLocator(String name) {
		this.name = name;
	}

	public Property<T> get(Entity entity) {
		return (Property<T>) entity.getProperty(name);
	}

	public String getName() {
		return name;
	}

	public T getValue(Entity entity) {
		Property<T> prop = get(entity);

		return prop != null ? prop.get() : null;
	}

	public void setValue(Entity entity, T value) {
		Property<Object> property = (Property<Object>) get(entity);
		if (property == null) {
			property = new Property<Object>(value);
			entity.addProperty(name, property);
		}
		property.set(value);
	}

}
