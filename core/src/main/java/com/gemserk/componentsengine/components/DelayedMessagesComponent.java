package com.gemserk.componentsengine.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.messages.GenericMessage;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.messages.UpdateMessage;
import com.gemserk.componentsengine.properties.Properties;
import com.google.inject.Inject;

public class DelayedMessagesComponent extends Component {

	private List<DelayedEntry> delayedMessages = new LinkedList<DelayedEntry>();

	@Inject
	MessageQueue messageQueue;

	public DelayedMessagesComponent(String id) {
		super(id);
	}

	@Handles
	public void delayedMessage(Message message) {
		Integer delay = Properties.getValue(message, "delay");
		Message delayedMessage = Properties.getValue(message, "message");
		delayedMessages.add(new DelayedEntry(delay, delayedMessage));
	}

	@Handles
	public void update(Message message) {
		int delta = (Integer)Properties.getValue(message, "delta");
		List<DelayedEntry> fired = new LinkedList<DelayedEntry>();
		for (Iterator iterator = delayedMessages.iterator(); iterator.hasNext();) {
			DelayedEntry entry = (DelayedEntry) iterator.next();
			if (entry.shouldSend(delta)) {
				fired.add(entry);
				iterator.remove();
			}
		}

		for (DelayedEntry delayedEntry : fired) {
			messageQueue.enqueue(delayedEntry.message);
		}

	}

	class DelayedEntry {
		int delay;
		Message message;

		public DelayedEntry(int delay, Message message) {
			this.delay = delay;
			this.message = message;
		}

		public boolean shouldSend(int delta) {
			delay -= delta;
			return delay < 0;
		}
	}

}
