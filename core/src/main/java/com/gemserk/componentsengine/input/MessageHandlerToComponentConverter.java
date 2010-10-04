package com.gemserk.componentsengine.input;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.MessageHandler;

public class MessageHandlerToComponentConverter extends Component{
	
	private final MessageHandler messageHandler;

	public MessageHandlerToComponentConverter(String id, MessageHandler messageHandler) {
		super(id);
		this.messageHandler = messageHandler;
	}
	
	@Override
	public void handleMessage(Message message) {
		messageHandler.handleMessage(message);
	}
	
}