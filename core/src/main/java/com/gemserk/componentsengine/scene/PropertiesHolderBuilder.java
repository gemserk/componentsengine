package com.gemserk.componentsengine.scene;

import com.gemserk.componentsengine.properties.PropertiesHolder;
import com.gemserk.componentsengine.properties.ReferenceProperty;
import com.gemserk.componentsengine.properties.SimpleProperty;

public class PropertiesHolderBuilder {

	private PropertiesHolder propertiesHolder;

	public PropertiesHolderBuilder(PropertiesHolder propertiesHolder) {
		this.propertiesHolder = propertiesHolder;
	}

	public void property(String key, Object value) {
		propertiesHolder.addProperty(key, new SimpleProperty<Object>(value));
	}

	public void propertyRef(String key, String referencedPropertyName) {
		propertiesHolder.addProperty(key, new ReferenceProperty<Object>(referencedPropertyName, propertiesHolder));
	}

}