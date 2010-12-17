package com.gemserk.componentsengine.messages;

public interface MessageProvider {

	Message createMessage(String id);

	void free(Message message);

}