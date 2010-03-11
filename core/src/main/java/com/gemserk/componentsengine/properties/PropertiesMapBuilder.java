package com.gemserk.componentsengine.properties;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class PropertiesMapBuilder {

	Map<String, Property<Object>> properties = new HashMap<String, Property<Object>>();

	public PropertiesMapBuilder property(String key, Object value) {
		properties.put(key, new SimpleProperty<Object>(value));
		return this;
	}

	public Map<String, Property<Object>> build() {
		return properties;
	}

	public PropertiesMapBuilder addProperties(Map<String, Object> map) {
		for (Entry<String, Object> entry : map.entrySet()) {
			property(entry.getKey(), entry.getValue());
		}
		return this;
	}

}