package com.gemserk.componentsengine.properties;

public interface SimplePropertyProvider {

	SimpleProperty createProperty(Object value);

	void free(SimpleProperty property);

}