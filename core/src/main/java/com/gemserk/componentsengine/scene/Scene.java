package com.gemserk.componentsengine.scene;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.components.MessageHandler;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.world.World;
import com.google.inject.Inject;

public class Scene implements MessageHandler {

	World world;
	
	Map<String, Component> components = new LinkedHashMap<String, Component>();

	@Inject
	public void setWorld(World world) {
		this.world = world;
	}
	
	public World getWorld() {
		return world;
	}

	@Override
	public void handleMessage(Message message) {
		
		for (Entry<String, Component> entry : components.entrySet()) {
			MessageHandler component = entry.getValue();
			component.handleMessage(message);
		}
		
		world.handleMessage(message);
	}
	
	public void addComponent(Component component) {
		components.put(component.getId(), component);
	}


}