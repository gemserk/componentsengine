package com.gemserk.componentsengine.messages;

import com.gemserk.componentsengine.entities.Entity;

public class AddEntityMessage extends Message {
	
	private final Entity entityToAdd;

	public AddEntityMessage(Entity entitytoadd) {
		this.entityToAdd = entitytoadd;
	}
	
	public Entity getEntityToAdd() {
		return entityToAdd;
	}
	
}
