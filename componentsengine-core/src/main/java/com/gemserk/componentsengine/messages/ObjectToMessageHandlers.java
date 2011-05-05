package com.gemserk.componentsengine.messages;

import java.util.Map;

public interface ObjectToMessageHandlers {
	public Map<String,MessageHandler> config(Object parameter);
}
