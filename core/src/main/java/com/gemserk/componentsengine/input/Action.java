/**
 * 
 */
package com.gemserk.componentsengine.input;

import groovy.lang.Closure;

import com.gemserk.componentsengine.messages.GenericMessage;
import com.gemserk.componentsengine.messages.Message;

public abstract class Action {
	private Closure closure;
	private String eventId;
	private String trigger;

	public Action(String trigger, String eventId, Closure closure) {
		this.trigger = trigger;
		this.eventId = eventId;
		this.closure = closure;
	}

	public Message run(Object delegate) {
		if (shouldRun()) {
			Message message = new GenericMessage(eventId);
			if (closure != null) {
				closure.setDelegate(delegate);
				closure.call(message);
			}
			return message;
		}
		return null;
	}

	public abstract boolean shouldRun();

}