package com.gemserk.componentsengine.input;

import com.gemserk.componentsengine.input.InputAction;
import com.gemserk.componentsengine.messages.Message;

public abstract class AbstractInputAction implements InputAction {

	private final String eventId;

	public AbstractInputAction(String eventId) {
		this.eventId = eventId;
	}

	@Override
	public Message run() {
		if (shouldRun()) {
			Message message = new Message(eventId);
			return message;
		}
		return null;
	}

	public abstract boolean shouldRun();

}