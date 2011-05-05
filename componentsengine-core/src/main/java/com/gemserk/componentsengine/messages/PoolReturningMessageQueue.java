package com.gemserk.componentsengine.messages;

import java.util.Queue;

import com.gemserk.componentsengine.utils.ArrayDeque;
import com.google.inject.Inject;

public class PoolReturningMessageQueue implements MessageQueue {

	Queue<Message> messages = new ArrayDeque<Message>();

	@Inject MessageDispatcher messageDispatcher;

	@Inject MessageProvider messageProvider;

	public void enqueue(Message message) {
		dispatchMessage(message);
	}
	
	public void enqueueDelay(Message message) {
		messages.add(message);
	}
	
	public void dispatchMessage(Message message){
		messageDispatcher.dispatch(message);
		messageProvider.free(message);
	}


	public void processMessages() {
		while (!messages.isEmpty()) {
			Message message = messages.poll();
			dispatchMessage(message);
		}
	}
}