package com.gemserk.componentsengine.messages.messageBuilder;

import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.SimpleProperty;

public class MessageBuilderImpl implements MessageBuilder, InitializedMessageBuilder {
	
	private Message message;
	
	public InitializedMessageBuilder init(Message message){
		this.message = message;
		return this;
	}
	
	public InitializedMessageBuilder property(String key, Object value){
		message.addProperty(key, new SimpleProperty<Object>(value));
		return this;
	}
	
	public Message get(){
		return message;
	}
	
}