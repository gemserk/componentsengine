package com.gemserk.componentsengine.properties;

public interface SimplePropertyProvider {

	@SuppressWarnings("rawtypes")
	SimpleProperty createProperty(Object value);

	@SuppressWarnings("rawtypes")
	void free(SimpleProperty property);

}