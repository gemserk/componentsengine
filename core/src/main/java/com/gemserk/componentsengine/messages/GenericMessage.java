package com.gemserk.componentsengine.messages;

import java.util.Map;

import com.gemserk.componentsengine.properties.PropertiesHolder;
import com.gemserk.componentsengine.properties.Property;

public class GenericMessage extends Message implements PropertiesHolder {

	public GenericMessage(String id) {
		super(id);
	}

	public GenericMessage(String id, Map<String, Property<Object>> properties) {
		super(id,properties);
	}


}