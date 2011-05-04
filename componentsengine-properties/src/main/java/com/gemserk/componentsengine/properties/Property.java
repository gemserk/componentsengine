package com.gemserk.componentsengine.properties;

public interface Property<T extends Object> {

	T get();

	void set(T value);

}