package com.gemserk.componentsengine.properties;

import groovy.lang.Closure;

public class ClosureProperty implements Property<Object> {

	private final Closure closure;

	public ClosureProperty(PropertiesHolder propertiesHolder, Closure closure) {
		this.closure = closure;
		closure.setProperty("entity", propertiesHolder);		
	}

	@Override
	public void set(Object value) {
		closure.call(value);
	}

	@Override
	public Object get() {
		return closure.call();
	}
}