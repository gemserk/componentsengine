package com.gemserk.componentsengine.messages;

import java.util.LinkedList;
import java.util.Queue;

import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.MessageDispatcher;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.google.inject.Inject;

public class PoolReturningMessageQueue implements MessageQueue {

	Queue<Message> messages = new LinkedList<Message>();

	@Inject MessageDispatcher messageDispatcher;

	@Inject MessageProvider messageProvider;

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
			messageProvider.free(message);
		}
	}
}