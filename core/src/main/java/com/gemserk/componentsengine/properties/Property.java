package com.gemserk.componentsengine.properties;

public interface Property<T extends Object> {

	public abstract T get();

	public abstract void set(T value);

}