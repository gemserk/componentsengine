package com.gemserk.componentsengine.components;

import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.messages.ChildrenManagementMessageFactory;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.properties.PropertyLocator;

public class ChildrenManagementComponent extends ReflectionComponent {

	private PropertyLocator<Entity> entityProperty = Properties.property(ChildrenManagementMessageFactory.PARAMETER_ENTITY);
	private PropertyLocator<String> removeEntityIdProperty = Properties.property(ChildrenManagementMessageFactory.PARAMETER_REMOVE_ENTITY_ID);
	private PropertyLocator<String> whereEntityIdProperty = Properties.property(ChildrenManagementMessageFactory.PARAMETER_WHERE_ENTITY_ID);
	
	public ChildrenManagementComponent(String id) {
		super(id);
	}

	@Handles(ids={ChildrenManagementMessageFactory.ADD_MESSAGE_ID})
	public void addEntity(Message message) {
		Entity entityToAdd = entityProperty.getValue(message);
		
		String whereEntityId = whereEntityIdProperty.getValue(message);
		
		Entity newParentEntity = null;

		
		if (entity.getId().equals(whereEntityId))
			newParentEntity = entity;
		else
			newParentEntity = entity.getEntityById(whereEntityId);
		
		if (newParentEntity != null)
			newParentEntity.addEntity(entityToAdd);
	}

	@Handles(ids={ChildrenManagementMessageFactory.REMOVE_MESSAGE_ID})
	public void removeEntity(Message message) {
		String entityToRemove = removeEntityIdProperty.getValue(message);
		Entity childEntity = entity.getEntityById(entityToRemove);
		if (childEntity != null)
			childEntity.removeFromParent();
	}
}
