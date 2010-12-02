package com.gemserk.componentsengine.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.messages.MessageDispatcher;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class EntityManager {

	MessageDispatcher messageDispatcher;
	
	@Inject
	public void setMessageDispatcher(MessageDispatcher messageDispatcher) {
		this.messageDispatcher = messageDispatcher;
	}

	class EntityRegistrator {

		Map<String, Entity> entities = new HashMap<String, Entity>();

		ArrayList<Entity> entityList = new ArrayList<Entity>();
		
		
		private void registerEntity(Entity entity) {
			if (getEntityById(entity.getId()) != null)
				throw new RuntimeException("entity with id " + entity.getId() + " already registered");
			for (Component component : entity.getComponents().values()) {
				messageDispatcher.registerComponent(component);
			}
			store(entity);
		}

		private void store(Entity entity) {
			entities.put(entity.getId(), entity);
			entityList.add(entity);//we throw exception if it is already registered
		}
		
		private void remove(Entity entity) {
			entities.remove(entity.getId());
			entityList.remove(entity);
		}

		public void registerEntities(List<Entity> entities) {
			for (Entity entity : entities) {
				registerEntity(entity);
			}
		}

		private void unregisterEntity(Entity entity) {
			for (Component component : entity.getComponents().values()) {
				messageDispatcher.unregisterComponent(component);
			}
			remove(entity);
		}

		

		public void unregisterEntities(List<Entity> entities) {
			for (Entity entity : entities) {
				unregisterEntity(entity);
			}
		}

		public Entity getEntityById(String id) {
			return entities.get(id);
		}

		public ArrayList<Entity> getEntities() {
			return entityList;
		}

	}

	EntityRegistrator entityRegistrator = new EntityRegistrator();

	public void addEntity(Entity entity) {
		addEntity(entity, null);
	}

	public void addEntity(Entity entity, String parentEntityId) {

		Entity oldEntity = entityRegistrator.getEntityById(entity.getId());
		if (oldEntity != null) {
			oldEntity.removeFromParent();
			entityRegistrator.unregisterEntities(plainTreeEntities(oldEntity));
		}
		
		if (parentEntityId != null) {
			Entity parentEntity = entityRegistrator.getEntityById(parentEntityId);
			if (parentEntity == null)
				throw new RuntimeException("trying to add entity to inexistent entity id: " + parentEntityId);
			parentEntity.addEntity(entity);
		}

		entityRegistrator.registerEntities(plainTreeEntities(entity));

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

	public void removeEntity(String entityName) {
		Entity entity = entityRegistrator.getEntityById(entityName);
		if (entity == null)
			return;
		entity.removeFromParent();
		entityRegistrator.unregisterEntities(plainTreeEntities(entity));
	}
	
	public  ArrayList<Entity> getEntities(){
		return entityRegistrator.getEntities();
	}

}
