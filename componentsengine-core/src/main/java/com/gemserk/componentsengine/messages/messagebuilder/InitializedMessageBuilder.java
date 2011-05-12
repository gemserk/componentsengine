package com.gemserk.componentsengine.messages.messagebuilder;

import com.gemserk.componentsengine.messages.Message;

public interface InitializedMessageBuilder {
	public InitializedMessageBuilder property(String key, Object value);
	public Message get();
}