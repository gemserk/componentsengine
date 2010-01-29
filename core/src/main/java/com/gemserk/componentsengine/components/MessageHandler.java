package com.gemserk.componentsengine.components;

import com.gemserk.componentsengine.messages.Message;

public interface MessageHandler {

	public abstract void handleMessage(Message message);

}