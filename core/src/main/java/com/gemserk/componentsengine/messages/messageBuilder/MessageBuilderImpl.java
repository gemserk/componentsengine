package com.gemserk.componentsengine.messages.messageBuilder;

import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.MessageProvider;
import com.gemserk.componentsengine.properties.SimpleProperty;
import com.gemserk.componentsengine.properties.SimpleProperyProvider;
import com.google.inject.Inject;

public class MessageBuilderImpl implements MessageBuilder, InitializedMessageBuilder {
	
	private Message message;
	
	@Inject MessageProvider messageProvider;
	@Inject SimpleProperyProvider simpleProperyProvider;
	
	public InitializedMessageBuilder newMessage(String id){
		this.message = messageProvider.createMessage(id);
		return this;
	}
	
	public InitializedMessageBuilder property(String key, Object value){
		SimpleProperty simpleProperty = simpleProperyProvider.createProperty(value);
		message.addProperty(key, simpleProperty);
		return this;
	}
	
	public Message get(){
		Message theMessage = message;
		message = null;
		return theMessage;
	}
	
}