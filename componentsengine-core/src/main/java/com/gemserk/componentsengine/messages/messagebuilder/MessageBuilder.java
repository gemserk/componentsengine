package com.gemserk.componentsengine.messages.messagebuilder;

import com.gemserk.componentsengine.messages.Message;


public interface MessageBuilder {
	InitializedMessageBuilder newMessage(String id);

	Message clone(Message origin);
}