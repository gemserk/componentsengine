package com.gemserk.componentsengine.scene;

import java.util.Map;
import java.util.Map.Entry;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.components.ComponentsHolder;
import com.gemserk.componentsengine.components.ComponentsHolderImpl;
import com.gemserk.componentsengine.components.MessageHandler;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.PropertiesHolder;
import com.gemserk.componentsengine.properties.PropertiesHolderImpl;
import com.gemserk.componentsengine.properties.Property;
import com.gemserk.componentsengine.world.World;
import com.google.inject.Inject;

public class Scene implements MessageHandler, PropertiesHolder, ComponentsHolder {

	String id;

	World world;

	PropertiesHolder propertiesHolder = new PropertiesHolderImpl();

	ComponentsHolderImpl componentsHolder = new ComponentsHolderImpl();

	@Inject
	public void setWorld(World world) {
		this.world = world;
	}

	public World getWorld() {
		return world;
	}

	public Scene(String id) {
		this.id = id;
	}

	@Override
	public void handleMessage(Message message) {

		message.setScene(this);
		for (Entry<String, Component> entry : componentsHolder.getComponents().entrySet()) {
			MessageHandler component = entry.getValue();
			component.handleMessage(message);
		}

		world.handleMessage(message);
	}

	public void addComponent(Component component) {
		componentsHolder.getComponents().put(component.getId(), component);
	}

	public void addProperty(String key, Property<Object> value) {
		propertiesHolder.addProperty(key, value);
	}

	public Map<String, Property<Object>> getProperties() {
		return propertiesHolder.getProperties();
	}

	public Property<Object> getProperty(String key) {
		return propertiesHolder.getProperty(key);
	}

}