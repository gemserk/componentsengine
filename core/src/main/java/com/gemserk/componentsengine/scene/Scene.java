package com.gemserk.componentsengine.scene;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.components.MessageHandler;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.world.World;

public class Scene implements MessageHandler {

	World world;
	
	Map<String, Component> components = new LinkedHashMap<String, Component>();

	public void setWorld(World world) {
		this.world = world;
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