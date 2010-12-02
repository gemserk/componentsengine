package com.gemserk.componentsengine.messages.messageBuilder;

import com.gemserk.componentsengine.messages.Message;

public interface InitializedMessageBuilder {
	public InitializedMessageBuilder property(String key, Object value);
	public Message get();
}