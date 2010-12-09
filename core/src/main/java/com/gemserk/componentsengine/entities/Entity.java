package com.gemserk.componentsengine.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.components.ComponentsHolder;
import com.gemserk.componentsengine.components.ComponentsHolderImpl;
import com.gemserk.componentsengine.properties.PropertiesHolder;
import com.gemserk.componentsengine.properties.PropertiesHolderImpl;
import com.gemserk.componentsengine.properties.Property;
import com.gemserk.componentsengine.utils.RandomAccessMap;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class Entity implements PropertiesHolder, ComponentsHolder {

	private final String id;

	PropertiesHolder propertiesHolder = new PropertiesHolderImpl();

	ComponentsHolderImpl componentsHolder = new ComponentsHolderImpl();

	Set<String> tags = new HashSet<String>();

	protected Entity parent = null;

	protected Map<String, Entity> children = new RandomAccessMap<String, Entity>();

	public Entity(String id) {
		this.id = id.intern();
	}

	public String getId() {
		return id;
	}
	
	public Entity getParent() {
		return parent;
	}

	public Map<String, Entity> getChildren() {
		return children;
	}
	
	public Map<String, Component> getComponents() {
		return componentsHolder.getComponents();
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

	public void addProperty(String key, Property<Object> property) {
		propertiesHolder.addProperty(key, property);
	}

	public Property<Object> getProperty(String key) {
		return propertiesHolder.getProperty(key);
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
		//return "Entity [id=" + id + ", tags=" + tags + ", components=" + componentsHolder.getComponents() + ", properties=" + propertiesHolder.getProperties() + ", children: " + children.size() + "]";
		return "Entity [id=" + id + ", tags=" + tags + " children: " + children.size() + "]";
	}

	public Map<String, Property<Object>> getProperties() {
		return propertiesHolder.getProperties();
	}

	public void addEntity(Entity entity) {
		this.children.put(entity.getId(), entity);
		entity.parent = this;
	}

	public void removeEntity(Entity entity) {
		this.children.remove(entity.getId());
		entity.parent = null;
	}

	public Collection<Entity> getEntities(Predicate<? super Entity> predicate) {
		return Lists.newArrayList(this.getChildrenIterable(predicate));
	}

	public Iterable<Entity> getChildrenIterable(Predicate<? super Entity> predicate) {
		List<Iterable<Entity>> iterables = new ArrayList<Iterable<Entity>>(this.children.size());

		for (Entity child : children.values()) {
			Iterable<Entity> childIterable = child.getChildrenIterable(predicate);
			iterables.add(childIterable);
		}

		if (predicate.apply(this))
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

	public void removeFromParent() {
		if (parent != null)
			parent.removeEntity(this);
	}

	public Entity getRoot() {
		if (parent == null)
			return this;

		return parent.getRoot();
	}
}
