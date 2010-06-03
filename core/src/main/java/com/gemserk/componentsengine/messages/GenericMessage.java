package com.gemserk.componentsengine.messages;

import java.util.HashMap;
import java.util.Map;

import com.gemserk.componentsengine.properties.PropertiesHolder;
import com.gemserk.componentsengine.properties.PropertiesHolderImpl;
import com.gemserk.componentsengine.properties.Property;

public class GenericMessage extends Message implements PropertiesHolder {

	String id;

	PropertiesHolder propertiesHolder = new PropertiesHolderImpl();

	public String getId() {
		return id;
	}

	public GenericMessage(String id) {
		this(id, new HashMap<String, Property<Object>>());
	}

	public GenericMessage(String id, Map<String, Property<Object>> properties) {
		this.id = id;
		this.propertiesHolder = new PropertiesHolderImpl(properties);
	}

	public void addProperty(String key, Property<Object> value) {
		propertiesHolder.addProperty(key, value);
	}

	public Map<String, Property<Object>> getProperties() {
		return propertiesHolder.getProperties();
	}

	public Property<Object> getProperty(String key) {
		return propertiesHolder.getProperty(key);
	}
	
	@Override
	public String toString() {
		return "GenericMessage: " + getId();
	}


}