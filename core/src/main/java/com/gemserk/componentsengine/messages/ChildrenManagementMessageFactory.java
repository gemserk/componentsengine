package com.gemserk.componentsengine.messages;

import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.properties.SimpleProperty;

public class ChildrenManagementMessageFactory  {
	
	public static final String PARAMETER_WHERE_ENTITY_ID = "whereEntityId";
	public static final String PARAMETER_REMOVE_ENTITY_ID = "removeEntityID";
	public static final String PARAMETER_ENTITY = "entity";
	public static final String ADD_MESSAGE_ID = "addEntity";
	public static final String REMOVE_MESSAGE_ID = "removeEntity";

	public static Message addEntity(Entity entitytoadd, String whereEntityId) {
		Message message = new Message(ADD_MESSAGE_ID);
		message.addProperty(PARAMETER_ENTITY, new SimpleProperty<Object>(entitytoadd));
		message.addProperty(PARAMETER_WHERE_ENTITY_ID, new SimpleProperty<Object>(whereEntityId));
		return message;
	}
	
	
	public static Message addEntity(Entity entitytoadd, Entity whereEntity) {
		return addEntity(entitytoadd, whereEntity.getId());
	}

	public static Message removeEntity(Entity entityToRemove) {
		return removeEntity(entityToRemove.getId());
	}


	public static Message removeEntity(String childName) {
		Message message = new Message(REMOVE_MESSAGE_ID);
		message.addProperty(PARAMETER_REMOVE_ENTITY_ID, new SimpleProperty<Object>(childName));
		return message;
	}

}
