package com.gemserk.componentsengine.properties;

public class ReferenceProperty<T extends Object> implements Property<T> {

	String referencedPropertyName;
	PropertiesHolder holder;
	
	public ReferenceProperty(String referencedPropertyName, PropertiesHolder holder) {
		this.referencedPropertyName = referencedPropertyName;
		this.holder = holder;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get() {
		return (T) holder.getProperty(referencedPropertyName).get();
	}

	@Override
	public void set(T value) {
		holder.getProperty(referencedPropertyName).set(value);
	}

	@Override
	public String toString() {
		return "REFPROP: " + referencedPropertyName;
	}
}
