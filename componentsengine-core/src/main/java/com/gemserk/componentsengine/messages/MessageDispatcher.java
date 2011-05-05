package com.gemserk.componentsengine.messages;

import com.gemserk.componentsengine.components.Component;

public interface MessageDispatcher {

	void registerComponent(Component component);

	void unregisterComponent(Component component);

	void dispatch(Message message);

}