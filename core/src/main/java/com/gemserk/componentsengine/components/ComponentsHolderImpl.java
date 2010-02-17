package com.gemserk.componentsengine.components;

import java.util.LinkedHashMap;
import java.util.Map;


public class ComponentsHolderImpl implements ComponentsHolder {

	private Map<String, Component> components = new LinkedHashMap<String, Component>();

	public Map<String, Component> getComponents() {
		return components;
	}

	public void addComponent(Component component) {
		components.put(component.getId(), component);
	}
}