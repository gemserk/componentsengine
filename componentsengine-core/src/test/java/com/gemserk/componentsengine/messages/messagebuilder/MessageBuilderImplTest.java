package com.gemserk.componentsengine.messages.messagebuilder;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.MessageProvider;
import com.gemserk.componentsengine.messages.messagebuilder.MessageBuilderImpl;
import com.gemserk.componentsengine.properties.Property;
import com.gemserk.componentsengine.properties.SimpleProperty;
import com.gemserk.componentsengine.properties.SimplePropertyProvider;

public class MessageBuilderImplTest {


	private static final String VALUE1 = "value1";
	private static final String KEY1 = "key1";
	private static final String MESSAGE_ID = "messageId";
	MessageBuilderImpl messageBuilder;

	@Before
	public void setUp() {
		messageBuilder = new MessageBuilderImpl();
		messageBuilder.messageProvider = new MessageProviderImplementation();
		messageBuilder.simpleProperyProvider = new SimplePropertyProviderImplementation();
	}

	
	@Test
	public void theMessageShouldBeNotNull(){
		Message message = messageBuilder.newMessage(MESSAGE_ID).get();
		Assert.assertNotNull(message);
	}
	
	@Test
	public void theMessageShouldHaveTheDesiredId(){
		Message message = messageBuilder.newMessage(MESSAGE_ID).get();
		Assert.assertEquals(MESSAGE_ID, message.getId());
	}
	
	
	
	@Test
	public void theMessageShouldTheAddedProperty(){
		Message message = messageBuilder.newMessage(MESSAGE_ID).property(KEY1, VALUE1).get();
		Assert.assertEquals(VALUE1, message.getProperty(KEY1).get());
	}
	
	
	@Test
	public void theClonedMessageShouldHaveTheSamePropertyValueButDifferentSimpleProperty(){
		Message message = messageBuilder.newMessage(MESSAGE_ID).property(KEY1, VALUE1).get();
		
		Message clonedMessage = messageBuilder.clone(message);
		
		Assert.assertNotSame(clonedMessage, message);
		Assert.assertEquals(message.getId(), clonedMessage.getId());
		Property<Object> propertyOriginal = message.getProperty(KEY1);
		Property<Object> propertyClone = clonedMessage.getProperty(KEY1);
		Assert.assertNotSame(propertyClone, propertyOriginal);
		
		Assert.assertEquals(propertyOriginal.get(), propertyClone.get());
	}
	
	private final class MessageProviderImplementation implements MessageProvider {
		@Override
		public Message createMessage(String id) {
			return new Message(id);
		}

		@Override
		public void free(Message message) {
		}
	}

	private final class SimplePropertyProviderImplementation implements SimplePropertyProvider {
		@Override
		public void free(SimpleProperty property) {
		}

		@Override
		public SimpleProperty createProperty(Object value) {
			return new SimpleProperty<Object>(value);
		}
	}

}
