/**
 * 
 */
package com.gemserk.componentsengine.properties;

import java.util.HashMap;
import java.util.Map;

import com.gemserk.componentsengine.properties.Property;
import com.gemserk.componentsengine.properties.SimpleProperty;

public class PropertiesMapBuilder {

	Map<String, Property<Object>> properties = new HashMap<String, Property<Object>>();

	public PropertiesMapBuilder property(String key, Object value) {
		properties.put(key, new SimpleProperty<Object>(value));
		return this;
	}

	public Map<String, Property<Object>> build() {
		return properties;
	}

}