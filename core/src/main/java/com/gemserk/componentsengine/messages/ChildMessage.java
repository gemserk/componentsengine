package com.gemserk.componentsengine.messages;

import com.gemserk.componentsengine.entities.Entity;

public class ChildMessage extends Message {

	public enum Operation {
		ADD, REMOVE
	}

	private final Operation operation;
	private final Entity entityToAdd;
	private final String whereEntityId;

	private ChildMessage(Entity entitytoadd, String whereEntityId, Operation operation) {
		this.entityToAdd = entitytoadd;
		this.whereEntityId = whereEntityId;
		this.operation = operation;

	}

	public static ChildMessage addEntity(Entity entitytoadd, String whereEntityId) {
		return new ChildMessage(entitytoadd, whereEntityId, Operation.ADD);
	}

	public static ChildMessage removeEntity(Entity entityToRemove) {
		return new ChildMessage(entityToRemove, null, Operation.REMOVE);
	}

	public Entity getEntityToAdd() {
		return entityToAdd;
	}

	public String getWhereEntityId() {
		return whereEntityId;
	}

	public Operation getOperation() {
		return operation;
	}
}
