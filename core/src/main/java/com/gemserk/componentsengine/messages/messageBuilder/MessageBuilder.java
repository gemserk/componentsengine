package com.gemserk.componentsengine.messages.messageBuilder;

import com.gemserk.componentsengine.messages.Message;


public interface MessageBuilder {
	InitializedMessageBuilder newMessage(String id);
}