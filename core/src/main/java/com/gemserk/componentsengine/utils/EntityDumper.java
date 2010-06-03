package com.gemserk.componentsengine.utils;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.properties.Property;

public class EntityDumper {
	public Map<String, Object> dumpEntity(final Entity entity) {
		HashMap<String, Object> map = new LinkedHashMap<String, Object>();

		map.put("id", entity.getId());
		map.put("tags", entity.getTags());

		map.put("properties", new LinkedHashMap<String, Object>() {
			{
				for (Entry<String, Property<Object>> entry : entity.getProperties().entrySet()) {
					put(entry.getKey(), entry.getValue().toString());
				}
			}
		});

		map.put("components", new LinkedHashMap<String, Object>() {
			{
				for (Component component : entity.getComponents().values()) {
					put(component.getId(), component.getClass());
				}
			}
		});

		map.put("children", new LinkedHashMap<String, Object>() {
			{
				for (Entity child : entity.getChildren().values()) {
					put(child.getId(), dumpEntity(child));
				}
			}
		});

		return map;
	}
}