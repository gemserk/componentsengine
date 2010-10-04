package com.gemserk.componentsengine.groovy.properties;

import com.gemserk.componentsengine.properties.PropertiesHolder;
import com.gemserk.componentsengine.properties.Property;

import groovy.lang.Closure;

public class ClosureProperty implements Property<Object> {

	private final Closure closure;
	

	public ClosureProperty(final PropertiesHolder propertiesHolder, Closure closure) {
		this.closure = closure;
		closure.setDelegate(new Object(){
			public PropertiesHolder getEntity(){
				return propertiesHolder;
			}
		});
		closure.setResolveStrategy(Closure.DELEGATE_FIRST);
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