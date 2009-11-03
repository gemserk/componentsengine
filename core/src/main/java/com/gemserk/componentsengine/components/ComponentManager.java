package com.gemserk.componentsengine.components;

import java.util.HashMap;
import java.util.Map;

public class ComponentManager {

	Map<String, Component> components = new HashMap<String, Component>();

	public void addComponent(Component component) {
		components.put(component.getId(), component);
	}

	public void addComponents(Component... components) {
		for (Component component : components)
			addComponent(component);
	}

	public Component getComponent(String name) {
		return components.get(name);
	}

	public Component[] getComponents(String... names) {
		Component[] components = new Component[names.length];
		for (int i = 0; i < names.length; i++)
			components[i] = getComponent(names[i]);
		return components;
	}

}
