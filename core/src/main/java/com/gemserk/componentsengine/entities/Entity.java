package com.gemserk.componentsengine.entities;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.components.MessageHandler;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.PropertiesHolder;
import com.gemserk.componentsengine.properties.PropertiesHolderImpl;
import com.gemserk.componentsengine.properties.Property;

public class Entity implements PropertiesHolder, MessageHandler {

	private final String id;

	Map<String, Component> components = new LinkedHashMap<String, Component>();

	PropertiesHolder propertiesHolder = new PropertiesHolderImpl();

	Set<String> tags = new HashSet<String>();

	public Entity(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void addComponent(Component component) {
		components.put(component.getId(), component);
		component.onAdd(this);
	}

	public void addComponents(Component... components) {
		for (Component component : components) {
			this.addComponent(component);
		}
	}


	public Component findComponentByName(String name) {
		return components.get(name);
	}
	
	public void addProperty(String key, Property<Object> value) {
		propertiesHolder.addProperty(key, value);
	}

	public Property<Object> getProperty(String key) {
		return propertiesHolder.getProperty(key);
	}

	public void handleMessage(Message message) {
		message.setEntity(this);
		for (Entry<String, Component> entry : components.entrySet()) {
			MessageHandler component = entry.getValue();
			component.handleMessage(message);
		}
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
		return "Entity [id=" + id + ", tags=" + tags + ", components=" + components + ", properties=" + propertiesHolder.getProperties() + "]";
	}

	public Map<String, Property<Object>> getProperties() {
		return propertiesHolder.getProperties();
	}
}
