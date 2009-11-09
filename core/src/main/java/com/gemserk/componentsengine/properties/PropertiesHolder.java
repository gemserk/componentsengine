package com.gemserk.componentsengine.properties;


public interface PropertiesHolder {

	void addProperty(String key, Property<Object> value);

	Property<Object> getProperty(String key);

}