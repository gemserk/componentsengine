package com.gemserk.componentsengine.components;

import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.messages.Message;

public abstract class Component implements MessageHandler {
	protected String id;

	public Component(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void onAdd(Entity entity){
	}
	
	public void handleMessage(Message message) {
	}
}
