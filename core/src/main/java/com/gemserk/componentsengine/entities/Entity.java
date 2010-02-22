package com.gemserk.componentsengine.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.components.ComponentsHolder;
import com.gemserk.componentsengine.components.ComponentsHolderImpl;
import com.gemserk.componentsengine.components.MessageHandler;
import com.gemserk.componentsengine.messages.ChildMessage;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.PropertiesHolder;
import com.gemserk.componentsengine.properties.PropertiesHolderImpl;
import com.gemserk.componentsengine.properties.Property;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class Entity implements PropertiesHolder, MessageHandler, ComponentsHolder {

	private final String id;

	PropertiesHolder propertiesHolder = new PropertiesHolderImpl();

	ComponentsHolderImpl componentsHolder = new ComponentsHolderImpl();

	Set<String> tags = new HashSet<String>();

	protected Map<String, Entity> children = new LinkedHashMap<String, Entity>(100);

	public Entity(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void addComponent(Component component) {
		componentsHolder.addComponent(component);
		component.onAdd(this);
	}

	public void addComponents(Component... components) {
		for (Component component : components) {
			this.addComponent(component);
		}
	}

	public Component findComponentByName(String name) {
		return componentsHolder.getComponents().get(name);
	}

	public void addProperty(String key, Property<Object> value) {
		propertiesHolder.addProperty(key, value);
	}

	public Property<Object> getProperty(String key) {
		return propertiesHolder.getProperty(key);
	}

	public void handleMessage(Message message) {

		if (handleChildrenModificationMessage(message))
			return;

		for (Entry<String, Component> entry : componentsHolder.getComponents().entrySet()) {
			MessageHandler component = entry.getValue();
			component.handleMessage(message);
		}

		for (Entry<String, Entity> entry : children.entrySet()) {
			Entity entity = entry.getValue();
			entity.handleMessage(message);
		}
	}

	private boolean handleChildrenModificationMessage(Message message) {

		if (!(message instanceof ChildMessage))
			return false;

		ChildMessage childMessage = (ChildMessage) message;
		if (!this.getId().equals(childMessage.getWhereEntityId()))
			return false;

		Entity entity = childMessage.getEntityToAdd();
		switch (childMessage.getOperation()) {
		case ADD:
			this.addEntity(entity);
			break;
		case REMOVE:
			this.removeEntity(entity);
			break;
		}

		return true;
	}

	public boolean hasTag(String tag) {
		return this.tags.contains(tag);
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void addTag(String tag) {
		tags.add(tag);
	}

	public void addTags(String... tags) {
		for (String tag : tags)
			addTag(tag);
	}

	@Override
	public String toString() {
		return "Entity [id=" + id + ", tags=" + tags + ", components=" + componentsHolder.getComponents() + ", properties=" + propertiesHolder.getProperties() + ", children: " + children.size() + "]";
	}

	public Map<String, Property<Object>> getProperties() {
		return propertiesHolder.getProperties();
	}

	public void addEntity(Entity entity) {
		this.children.put(entity.getId(), entity);
	}

	public void removeEntity(Entity entity) {
		this.children.remove(entity.getId());
	}

	public Collection<Entity> getEntities(Predicate<? super Entity> predicate) {
		return Lists.newArrayList(this.getChildrenIterable(predicate));		
	}
	
	public Iterable<Entity> getChildrenIterable(Predicate<? super Entity> predicate){
		List<Iterable<Entity>> iterables = new ArrayList<Iterable<Entity>>(this.children.size());
		
		for (Entity child: children.values()) {
			Iterable<Entity> childIterable = child.getChildrenIterable(predicate);
			iterables.add(childIterable);
		}
		
		if(predicate.apply(this))
			iterables.add(Lists.newArrayList(this));
		
		return Iterables.concat(iterables);
	}
	
	
	

	public Entity getEntityById(String id) {
		Entity entity = children.get(id);
		
		if (entity != null)
			return entity;
		
		for (Entry<String, Entity> entry : children.entrySet()) {
			Entity child = entry.getValue();
			entity = child.getEntityById(id);
			if (entity != null)
				return entity;
		}
		
		return null;
	}
}
