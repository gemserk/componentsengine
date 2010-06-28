package com.gemserk.componentsengine.entities;

import java.util.ArrayList;
import java.util.List;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.messages.MessageDispatcher;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class EntityManager {

	@Inject
	MessageDispatcher messageDispatcher;

	public void addEntity(Entity entity, Entity newParentEntity) {
		if (newParentEntity != null) {

			Entity previousChildEntity = newParentEntity.children.get(entity.getId());
			if (previousChildEntity != null)
				removeEntity(previousChildEntity);
		
			newParentEntity.addEntity(entity);
		}

		List<Entity> entities = plainTreeEntities(entity);
		for (Entity currentEntity : entities) {
			for (Component component : currentEntity.getComponents().values()) {
				messageDispatcher.registerComponent(component);
			}
		}

	}

	public void removeEntity(Entity entity) {
		entity.removeFromParent();
		List<Entity> entities = plainTreeEntities(entity);
		for (Entity currentEntity : entities) {
			for (Component component : currentEntity.getComponents().values()) {
				messageDispatcher.unregisterComponent(component);
			}
		}
	}

	private List<Entity> plainTreeEntities(Entity entity) {

		ArrayList<Entity> entities = Lists.newArrayList(entity);
		if (!entity.children.isEmpty()) {
			for (Entity child : entity.children.values()) {
				entities.addAll(plainTreeEntities(child));
			}
		}
		return entities;
	}
}
