package com.gemserk.componentsengine.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.messages.MessageDispatcher;
import com.gemserk.componentsengine.utils.CachingFastMap;
import com.gemserk.componentsengine.utils.RandomAccess;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class EntityManager {

	MessageDispatcher messageDispatcher;
	
	ArrayList<Entity> plainEntitiesReusedList = new ArrayList<Entity>();
	
	
	@Inject
	public void setMessageDispatcher(MessageDispatcher messageDispatcher) {
		this.messageDispatcher = messageDispatcher;
	}

	class EntityRegistrator {

		Map<String, Entity> entities = new CachingFastMap<String, Entity>();

		ArrayList<Entity> entityList = new ArrayList<Entity>();
		
		
		private void registerEntity(Entity entity) {
			if (getEntityById(entity.getId()) != null)
				throw new RuntimeException("entity with id " + entity.getId() + " already registered");
			
			RandomAccess<Component> components = (RandomAccess<Component>) entity.getComponents();
			for (int i = 0; i < components.size(); i++) {
				messageDispatcher.registerComponent(components.get(i));
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

		public void registerEntities(ArrayList<Entity> entities) {
			for (int i = 0; i < entities.size(); i++) {
				registerEntity(entities.get(i));
			}
		}

		private void unregisterEntity(Entity entity) {
			RandomAccess<Component> components = (RandomAccess<Component>) entity.getComponents();
			for (int i = 0; i < components.size(); i++) {
				messageDispatcher.unregisterComponent(components.get(i));
			}
			remove(entity);
		}

		

		public void unregisterEntities(ArrayList<Entity> entities) {
			for (int i = 0; i < entities.size(); i++) {
				unregisterEntity(entities.get(i));
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
			plainEntitiesReusedList.clear();
		}
		
		if (parentEntityId != null) {
			Entity parentEntity = entityRegistrator.getEntityById(parentEntityId);
			if (parentEntity == null)
				throw new RuntimeException("trying to add entity to inexistent entity id: " + parentEntityId);
			parentEntity.addEntity(entity);
		}

		entityRegistrator.registerEntities(plainTreeEntities(entity));
		plainEntitiesReusedList.clear();//to avoid keeping references and generating a memory leak
	}

	
	
	private ArrayList<Entity> plainTreeEntities(Entity entity) {
		innerPlainTreeEntities(plainEntitiesReusedList, entity);
		return plainEntitiesReusedList;
	}
	
	private void innerPlainTreeEntities(List<Entity> resultEntities, Entity entity) {
		resultEntities.add(entity);
		
		RandomAccess<Entity> children = (RandomAccess<Entity>) entity.children;
		for (int i = 0; i < children.size(); i++) {
			innerPlainTreeEntities(resultEntities, children.get(i));
		}
	}

	public void removeEntity(String entityName) {
		Entity entity = entityRegistrator.getEntityById(entityName);
		if (entity == null)
			return;
		entity.removeFromParent();
		entityRegistrator.unregisterEntities(plainTreeEntities(entity));
		plainEntitiesReusedList.clear();//to avoid keeping references and generating a memory leak
	}
	
	public  ArrayList<Entity> getEntities(){
		return entityRegistrator.getEntities();
	}

}
