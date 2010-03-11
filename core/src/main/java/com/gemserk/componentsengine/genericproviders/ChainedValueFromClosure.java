package com.gemserk.componentsengine.genericproviders;

import groovy.lang.Closure;

public class ChainedValueFromClosure implements GenericProvider {
	
	Closure closure;
	final GenericProvider genericProvider;
	
	
	public ChainedValueFromClosure(GenericProvider genericProvider, Closure closure) {
		this.genericProvider = genericProvider;
		this.closure = closure;	
	}
		
	public <T> T get(){
		Object t = (Object)get(new Object[] { });
		return (T)t;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Object... objects) {
		Object generated = genericProvider.get(objects);
		Object[] newParams = new Object[objects.length +1];
		newParams[0] = generated;
		System.arraycopy(objects, 0, newParams, 1, objects.length);
		return (T) closure.call(newParams);
	}

}
