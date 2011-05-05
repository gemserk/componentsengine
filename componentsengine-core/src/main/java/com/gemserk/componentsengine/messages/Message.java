package com.gemserk.componentsengine.messages;

import java.util.Map;

import com.gemserk.componentsengine.properties.PropertiesHolder;
import com.gemserk.componentsengine.properties.PropertiesHolderImpl;
import com.gemserk.componentsengine.properties.Property;
import com.gemserk.componentsengine.utils.RandomAccessMap;


public class Message implements PropertiesHolder {
	
	protected String id;
	
	final protected PropertiesHolder propertiesHolder;
	
	public Message(String id) {
		this(id, new RandomAccessMap<String, Property<Object>>());
	}

	public Message(String id, Map<String, Property<Object>> properties) {
		this.id = id;
		this.propertiesHolder = new PropertiesHolderImpl(properties);
	}
	
	public Message() {
		this("Message");
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
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
