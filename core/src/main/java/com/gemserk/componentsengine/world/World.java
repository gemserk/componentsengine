package com.gemserk.componentsengine.world;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.gemserk.componentsengine.components.MessageHandler;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.messages.AddEntityMessage;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.RemoveEntityMessage;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

public class World implements MessageHandler {
	Map<String, Entity> entities = new LinkedHashMap<String, Entity>(100);

	public World() {
	}

	public void addEntity(Entity entity) {
		this.entities.put(entity.getId(), entity);
	}

	public void removeEntity(Entity entity) {
		this.entities.remove(entity.getId());
	}

	public void removeEntity(String entityId) {
		this.entities.remove(entityId);
	}

	public void handleMessage(Message message) {
		
		if (message instanceof AddEntityMessage) {
			AddEntityMessage addMessage = (AddEntityMessage) message;
			this.addEntity(addMessage.getEntityToAdd());
		}
		if (message instanceof RemoveEntityMessage) {
			RemoveEntityMessage removeMessage = (RemoveEntityMessage) message;
			this.removeEntity(removeMessage.getEntityToRemove());
		}
		
		for (Entry<String, Entity> entry : entities.entrySet()) {
			Entity entity = entry.getValue();
			entity.handleMessage(message);
		}
	}

	public Collection<Entity> getEntities(Predicate<? super Entity> predicate) {
		return Collections2.filter(this.entities.values(), predicate);
	}

	/**
	 * removes all entities in the world
	 */
	public void clear() {
		entities.clear();
	}

	public Entity getEntityById(String id) {
		return entities.get(id);
	}

}
