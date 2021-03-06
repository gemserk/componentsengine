package com.gemserk.componentsengine.properties;

public class ReferenceProperty<T extends Object> implements Property<T> {

	private String referencedPropertyName;
	
	private PropertiesHolder holder;
	
	private Property<T> cachedProperty;

	public ReferenceProperty(String referencedPropertyName) {
		this(referencedPropertyName, null);
	}

	public ReferenceProperty(String referencedPropertyName, PropertiesHolder holder) {
		this.referencedPropertyName = referencedPropertyName.intern();
		this.holder = holder;
	}

	public void setPropertiesHolder(PropertiesHolder entity) {
		this.holder = entity;
		this.cachedProperty = null;
	}

	@Override
	public T get() {
		return (T) getProperty().get();
	}

	@SuppressWarnings("unchecked")
	private Property<T> getProperty() {
		if (cachedProperty == null)
			cachedProperty = (Property<T>) holder.getProperty(referencedPropertyName);
		return cachedProperty;
	}

	@Override
	public void set(T value) {
		getProperty().set(value);
	}

	@Override
	public String toString() {
		return "REFPROP: " + referencedPropertyName;
	}
}
