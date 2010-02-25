package com.gemserk.componentsengine.reflection.wrapper;

import com.gemserk.componentsengine.entities.Entity;

public interface ComponentPropertiesWrapper {

	/**
	 * Sets component's fields annotated with EntityProperty from entity properties with field's name.
	 */
	void importFrom(Entity entity);

	/**
	 * Sets Entity properties from Component fields with property's name, annotated with EntityPropety. 
	 */
	void exportTo(Entity entity);

}