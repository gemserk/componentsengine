package com.gemserk.componentsengine.properties;

import java.util.Map;


public interface PropertiesHolder {

	void addProperty(String key, Property<Object> value);

	Property<Object> getProperty(String key);

	Map<String, Property<Object>> getProperties();

}