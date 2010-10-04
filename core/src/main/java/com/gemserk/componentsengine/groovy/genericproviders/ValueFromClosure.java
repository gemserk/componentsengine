package com.gemserk.componentsengine.groovy.genericproviders;

import com.gemserk.componentsengine.genericproviders.GenericProvider;

import groovy.lang.Closure;

public class ValueFromClosure implements GenericProvider {
	
	Closure closure;
	
	public ValueFromClosure(Closure closure) {
		this.closure = closure;	
	}
		
	public <T> T get(){
		Object t = (Object)get(new Object[] { });
		return (T) t;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Object... objects) {
		return (T) closure.call(objects);
	}

}
