package com.gemserk.componentsengine.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.messages.Message;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

public class World {
	Map<String, Entity> entities = new LinkedHashMap<String, Entity>(100);
	
	List<Entity> queuedAdds = new ArrayList<Entity>();
	List<Entity> queuedRemoves = new ArrayList<Entity>();
	
	public World() {
	}
	
	public void addEntity(Entity entity){
		this.entities.put(entity.getId(), entity);
	}
	
	public void removeEntity(Entity entity){
		this.entities.remove(entity.getId());
	}
	
	public void removeEntity(String entityId){
		this.entities.remove(entityId);
	}
	
	public void queueAddEntity(Entity entity){
		this.queuedAdds.add(entity);
	}
	
	public void queueRemoveEntity(Entity entity){
		this.queuedRemoves.add(entity);
	}
	
	public void processPending(){
		for (Entity entity : queuedAdds) {
			this.addEntity(entity);
		}
		queuedAdds.clear();
		for (Entity entity : queuedRemoves) {
			this.removeEntity(entity);
		}
		queuedRemoves.clear();
	}
	
	public void broadcastMessage(Message message){
		for (Entry<String, Entity> entry : entities.entrySet()) {
			Entity entity = entry.getValue();
			entity.handleMessage(message);
		}
	}

	public Collection<Entity> getEntities(Predicate<? super Entity> predicate) {
		return Collections2.filter(this.entities.values(), predicate);
	}
	
}
