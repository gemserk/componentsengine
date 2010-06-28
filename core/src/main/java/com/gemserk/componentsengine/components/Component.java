package com.gemserk.componentsengine.components;

import java.util.Set;

import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.messages.Message;

public class Component implements MessageHandler {
	
	protected String id;
	
	protected Entity entity;

	public Component(String id) {
		this.id = id;
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
	
	public Set<String> getMessageIds(){
		return null;
	}
}
