package com.gemserk.componentsengine.android;

import java.util.LinkedList;
import java.util.Queue;

import com.gemserk.componentsengine.components.ReflectionComponent;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.google.inject.Inject;

public class MessageIntermediator extends ReflectionComponent {

	Queue<Message> storedMessages = new LinkedList<Message>();
	
	@Inject MessageQueue messageQueue;
	
	public MessageIntermediator() {
		super("MessageIntermediator");
	}
	
	@Handles
	public void update(Message message){
		
		while (!storedMessages.isEmpty()) {
			Message messageToSend = storedMessages.poll();
			messageQueue.enqueue(messageToSend);
		}
	}
	
	public void postMessage(Message message){
		storedMessages.add(message);
	}

}
