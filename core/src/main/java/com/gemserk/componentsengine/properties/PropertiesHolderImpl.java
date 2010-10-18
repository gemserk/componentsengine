/**
 * 
 */
package com.gemserk.componentsengine.properties;

import java.util.HashMap;
import java.util.Map;


public class PropertiesHolderImpl implements PropertiesHolder {

	Map<String, Property<Object>> properties;
	
	public PropertiesHolderImpl() {
		this(new HashMap<String, Property<Object>>());
	}
	
	public PropertiesHolderImpl(Map<String, Property<Object>> properties) {
		this.properties = properties;
		
	}

	@Override
	public void addProperty(String key, Property<Object> value) {
		this.properties.put(key.intern(), value);
	}

	@Override
	public Property<Object> getProperty(String key) {
		return this.properties.get(key);
	}
	
	public Map<String, Property<Object>> getProperties() {
		return properties;
	}
	
	
	@Override
	public String toString() {
		return properties.toString();
	}
}