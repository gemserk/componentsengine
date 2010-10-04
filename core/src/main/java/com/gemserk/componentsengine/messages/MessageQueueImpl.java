/**
 * 
 */
package com.gemserk.componentsengine.messages;

import java.util.LinkedList;
import java.util.Queue;

import com.google.inject.Inject;

public class MessageQueueImpl implements MessageQueue {

	Queue<Message> messages = new LinkedList<Message>();

	MessageHandler messageHandler;

	@Inject
	public void setMessageHandler(MessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}

	public void enqueue(Message message) {
		messageHandler.handleMessage(message);
	}
	
	public void enqueueDelay(Message message) {
		messages.add(message);
		//messageHandler.handleMessage(message);
	}


	public void processMessages() {
		while (!messages.isEmpty()) {
			Message message = messages.poll();
			messageHandler.handleMessage(message);
		}
	}
}