package com.gemserk.componentsengine.messages;

import com.gemserk.componentsengine.entities.Entity;

public class RemoveEntityMessage extends Message {

	private final String entityName;
	
	public String getEntityName() {
		return entityName;
	}

	public RemoveEntityMessage(String entityName) {
		this.entityName = entityName;
	}

	public RemoveEntityMessage(Entity entity) {
		this(entity.getId());
	}

}
