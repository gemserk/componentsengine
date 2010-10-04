package com.gemserk.componentsengine.groovy.triggers;

import com.gemserk.componentsengine.triggers.Trigger;

import groovy.lang.Closure;

public class ClosureTrigger implements Trigger {

	Closure closure;

	public ClosureTrigger(Closure closure) {
		this.closure = closure;
	}

	@Override
	public void trigger(Object... parameters) {
		closure.call(parameters);
	}

	@Override
	public void trigger() {
		trigger(new Object[] {});
	}

}
