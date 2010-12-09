package com.gemserk.componentsengine.components;

import java.util.Map;

import com.gemserk.componentsengine.utils.RandomAccessMap;


public class ComponentsHolderImpl implements ComponentsHolder {

	private Map<String, Component> components = new RandomAccessMap<String, Component>();

	public Map<String, Component> getComponents() {
		return components;
	}

	public void addComponent(Component component) {
		components.put(component.getId(), component);
	}
}