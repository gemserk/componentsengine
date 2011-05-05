/**
 * 
 */
package com.gemserk.componentsengine.messages;

import java.util.LinkedList;
import java.util.Queue;

import com.google.inject.Inject;

public class MessageQueueImpl implements MessageQueue {

	Queue<Message> messages = new LinkedList<Message>();

	@Inject MessageDispatcher messageDispatcher;

	

	public void enqueue(Message message) {
		messageDispatcher.dispatch(message);
	}
	
	public void enqueueDelay(Message message) {
		messages.add(message);
	}


	public void processMessages() {
		while (!messages.isEmpty()) {
			Message message = messages.poll();
			messageDispatcher.dispatch(message);
		}
	}
}