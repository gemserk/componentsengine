package com.gemserk.componentsengine.messages;

import com.gemserk.componentsengine.entities.Entity;

public class RemoveEntityMessage extends Message {
	
	private final Entity entityToRemove;

	public RemoveEntityMessage(Entity entityToRemove) {
		this.entityToRemove = entityToRemove;
	}
	
	public Entity getEntityToRemove() {
		return entityToRemove;
	}
	
}
