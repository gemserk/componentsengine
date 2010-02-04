package com.gemserk.componentsengine.messages;

import java.util.HashMap;
import java.util.Map;

import com.gemserk.componentsengine.properties.PropertiesHolder;
import com.gemserk.componentsengine.properties.Property;

public class GenericMessage extends Message implements PropertiesHolder {

	String id;

	Map<String, Property<Object>> properties;

	public String getId() {
		return id;
	}

	public GenericMessage(String id) {
		this(id, new HashMap<String, Property<Object>>());
	}

	public GenericMessage(String id, Map<String, Property<Object>> properties) {
		this.id = id;
		this.properties = properties;
	}

	@Override
	public void addProperty(String key, Property<Object> value) {
		properties.put(key, value);
	}

	@Override
	public Property<Object> getProperty(String key) {
		return properties.get(key);
	}

}