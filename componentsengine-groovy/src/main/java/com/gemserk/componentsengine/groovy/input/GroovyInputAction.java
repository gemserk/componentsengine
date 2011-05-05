package com.gemserk.componentsengine.groovy.input;

import groovy.lang.Closure;

import com.gemserk.componentsengine.input.InputAction;
import com.gemserk.componentsengine.messages.Message;

public abstract class GroovyInputAction implements InputAction {
	
	private Closure closure;
	
	private String eventId;

	public GroovyInputAction(String eventId, Closure closure) {
		this.eventId = eventId;
		this.closure = closure;
	}

	public Message run() {
		if (shouldRun()) {
			Message message = new Message(eventId);
			if (closure != null) {
				closure.call(message);
			}
			return message;
		}
		return null;
	}

	public abstract boolean shouldRun();

}