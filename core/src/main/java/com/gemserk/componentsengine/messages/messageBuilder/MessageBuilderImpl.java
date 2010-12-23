package com.gemserk.componentsengine.messages.messageBuilder;

import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.MessageProvider;
import com.gemserk.componentsengine.properties.Property;
import com.gemserk.componentsengine.properties.SimpleProperty;
import com.gemserk.componentsengine.properties.SimplePropertyProvider;
import com.gemserk.componentsengine.utils.RandomAccessWithKey;
import com.google.inject.Inject;

public class MessageBuilderImpl implements MessageBuilder, InitializedMessageBuilder {
	
	private Message message;
	
	@Inject MessageProvider messageProvider;
	@Inject SimplePropertyProvider simpleProperyProvider;
	
	public InitializedMessageBuilder newMessage(String id){
		this.message = createMessage(id);
		return this;
	}

	public InitializedMessageBuilder property(String key, Object value){
		addPropertyToMessage(message, key, value);
		return this;
	}

	public Message get(){
		Message theMessage = message;
		message = null;
		return theMessage;
	}
	
	public Message clone(Message origin){
		Message theMessage = createMessage(origin.getId());
		RandomAccessWithKey<String,Property<Object>> properties =  (RandomAccessWithKey<String, Property<Object>>) origin.getProperties();
		for (int i = 0; i < properties.size(); i++) {
			addPropertyToMessage(theMessage, properties.getKey(i),properties.get(i).get());
		}
		return theMessage;
	}
	
	public Message createMessage(String id) {
		return messageProvider.createMessage(id);
	}
	
	public void addPropertyToMessage(Message theMessage, String key, Object value) {
		SimpleProperty simpleProperty = simpleProperyProvider.createProperty(value);
		theMessage.addProperty(key, simpleProperty);
	}
	
}