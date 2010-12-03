package com.gemserk.componentsengine.messages;

import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.messages.messageBuilder.MessageBuilder;
import com.google.inject.Inject;

public class ChildrenManagementMessageFactory {

	public static final String PARAMETER_WHERE_ENTITY_ID = "whereEntityId";
	public static final String PARAMETER_REMOVE_ENTITY_ID = "removeEntityID";
	public static final String PARAMETER_ENTITY = "entity";
	public static final String ADD_MESSAGE_ID = "addEntity";
	public static final String REMOVE_MESSAGE_ID = "removeEntity";

	@Inject
	MessageBuilder messageBuilder;

	public Message addEntity(Entity entitytoadd, String whereEntityId) {
		Message message = messageBuilder.newMessage(ADD_MESSAGE_ID).property(PARAMETER_ENTITY, entitytoadd).property(PARAMETER_WHERE_ENTITY_ID, whereEntityId).get();
		return message;
	}

	public Message addEntity(Entity entitytoadd, Entity whereEntity) {
		return addEntity(entitytoadd, whereEntity.getId());
	}

	public Message removeEntity(Entity entityToRemove) {
		return removeEntity(entityToRemove.getId());
	}

	public Message removeEntity(String childName) {
		Message message = messageBuilder.newMessage(REMOVE_MESSAGE_ID).property(PARAMETER_REMOVE_ENTITY_ID, childName).get();
		return message;
	}

}
