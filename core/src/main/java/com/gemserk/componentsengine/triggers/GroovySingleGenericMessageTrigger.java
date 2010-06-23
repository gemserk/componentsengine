package com.gemserk.componentsengine.triggers;

import groovy.lang.Closure;

import java.util.Map;

import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.properties.PropertiesMapBuilder;

public class GroovySingleGenericMessageTrigger implements Trigger {

	String messageId;

	Closure closure;

	final MessageQueue messageQueue;

	public GroovySingleGenericMessageTrigger(String messageId, MessageQueue messageQueue, Closure closure) {
		this.messageId = messageId;
		this.messageQueue = messageQueue;
		this.closure = closure;
	}

	@Override
	public void trigger(){
		trigger(new Object[]{});
	}
	
	@Override
	public void trigger(Object ...parameters){
		
		Message message = parameters.length == 0 ? new Message(messageId) : new Message(messageId, new PropertiesMapBuilder().addProperties((Map)parameters[0]).build());

		closure.setProperty("message", message);
		closure.call();

		messageQueue.enqueue(message);
	}

}
