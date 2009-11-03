package com.gemserk.componentsengine.components;

import java.util.HashMap;
import java.util.Map;

public class ComponentManager {

	Map<String,Component> components = new HashMap<String, Component>();
	
	public void addComponent(Component component){
		components.put(component.getId(), component);
	}
	
	public Component getComponent(String name) {
		return components.get(name);
	}
	
}
