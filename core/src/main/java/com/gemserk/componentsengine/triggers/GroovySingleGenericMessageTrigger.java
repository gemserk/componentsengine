package com.gemserk.componentsengine.triggers;

import groovy.lang.Closure;

import java.util.Map;

import com.gemserk.componentsengine.messages.GenericMessage;
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
		
		GenericMessage message = parameters.length == 0 ? new GenericMessage(messageId) : new GenericMessage(messageId, new PropertiesMapBuilder().addProperties((Map)parameters[0]).build());

		closure.setProperty("message", message);
		closure.call();

		messageQueue.enqueue(message);
	}

}
