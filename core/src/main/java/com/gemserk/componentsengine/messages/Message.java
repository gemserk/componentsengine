package com.gemserk.componentsengine.messages;

import com.gemserk.componentsengine.entities.Entity;

public class Message {
	Entity entity;
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	public Entity getEntity() {
		return entity;
	}
}
