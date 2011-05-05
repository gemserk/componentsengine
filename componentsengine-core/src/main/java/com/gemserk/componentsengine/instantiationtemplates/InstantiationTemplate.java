package com.gemserk.componentsengine.instantiationtemplates;

import com.gemserk.componentsengine.entities.Entity;

public interface InstantiationTemplate {

	Entity get();

	Entity get(Object... parameters);

	/**
	 * @param id used to new instantiated entities 
	 * @return an IntstantiationTemplate which instantiates entities with specified id
	 */
	InstantiationTemplate withId(String id);
	
}