package com.gemserk.componentsengine.messages;

import com.gemserk.componentsengine.entities.Entity;

public class ChildrenManagementMessageFactory  {

	public static Message addEntity(Entity entitytoadd, String whereEntityId) {
		return new AddEntityMessage(entitytoadd, whereEntityId);
	}

	public static Message removeEntity(Entity entityToRemove) {
		return new RemoveEntityMessage(entityToRemove);
	}

}
