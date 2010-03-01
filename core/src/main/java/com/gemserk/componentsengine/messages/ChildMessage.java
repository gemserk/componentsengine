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

	// TODO: remove whereEntityId.
	public static ChildMessage removeEntity(Entity entityToRemove, String whereEntityId) {
		return new ChildMessage(entityToRemove, whereEntityId, Operation.REMOVE);
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
