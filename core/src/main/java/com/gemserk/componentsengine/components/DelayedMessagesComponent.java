package com.gemserk.componentsengine.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
	
	@Override
	public void handleMessage(Message message) {
		if(message instanceof GenericMessage){
			GenericMessage genericMessage = (GenericMessage) message;
			if(genericMessage.getId().equals("delayedMessage")){
				Integer delay = Properties.getValue(genericMessage,"delay");
				Message delayedMessage = Properties.getValue(genericMessage,"message");
				delayedMessages .add(new DelayedEntry(delay,delayedMessage));
			}
			return;
		}
		if(message instanceof UpdateMessage){
			UpdateMessage updateMessage = (UpdateMessage) message;
			List<DelayedEntry> fired = new LinkedList<DelayedEntry>();
			for (Iterator iterator = delayedMessages.iterator(); iterator.hasNext();) {
				DelayedEntry entry = (DelayedEntry) iterator.next();
				if(entry.shouldSend(updateMessage.getDelta())){
					fired.add(entry);
					iterator.remove();
				}				
			}
			
			for (DelayedEntry delayedEntry : fired) {
				messageQueue.enqueue(delayedEntry.message);
			}
			return;
		}
		
	}

	
	class DelayedEntry {
		int delay;
		Message message;
		
		public DelayedEntry(int delay, Message message) {
			this.delay = delay;
			this.message = message;
		}
		
		public boolean shouldSend(int delta){
			delay -=delta;
			return delay < 0;
		}
	}
	
}
