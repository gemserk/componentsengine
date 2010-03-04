package com.gemserk.componentsengine.messages;

import com.gemserk.componentsengine.entities.Entity;

public class AddEntityMessage extends Message {

	private final Entity entity;

	private final String whereEntityId;
	
	public Entity getEntity() {
		return entity;
	}
	
	public String getWhereEntityId() {
		return whereEntityId;
	}
	
	public AddEntityMessage(Entity entity, String whereEntityId) {
		this.entity = entity;
		this.whereEntityId = whereEntityId;
	}

}
