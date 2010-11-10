package com.gemserk.componentsengine.properties;

import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.properties.Property;

public class EntityPropertyReference<T> {

	String key;
	Entity entity;
	Property<T> cachedProperty;

	public EntityPropertyReference(String key) {
		this.key = key.intern();
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
		this.cachedProperty = null;
	}

	public T get() {
		Property<T> property = getProperty();

		return property != null ? (T) property.get() : null;
	}

	public Property<T> getProperty() {
		if (cachedProperty == null) {
			cachedProperty = (Property<T>) entity.getProperty(key);
		}
		return cachedProperty;
	}

	public void set(T value) {
		Property<T> property = getProperty();

		if (property != null)
			property.set(value);

	}

}
