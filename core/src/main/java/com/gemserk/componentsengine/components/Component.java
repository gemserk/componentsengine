package com.gemserk.componentsengine.components;

import java.util.Set;

import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.MessageHandler;

public class Component implements MessageHandler {
	
	protected String id;
	
	protected Entity entity;

	public Component(String id) {
		this.id = id.intern();
	}

	public String getId() {
		return id;
	}

	public void onAdd(Entity entity){
		this.entity = entity;
	}
	
	protected void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	public void handleMessage(Message message) {
	}
	
	public String[] getMessageIds(){
		return null;
	}
	
	@Override
	public String toString() {
		if(entity!=null)
			return String.format("Component: id - %s  : entityId: %s", id,entity.getId());
		else
			return String.format("Component: id - %s", id);
	}
}
