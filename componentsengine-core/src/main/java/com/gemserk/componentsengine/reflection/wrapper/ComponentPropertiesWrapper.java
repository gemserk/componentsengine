package com.gemserk.componentsengine.reflection.wrapper;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.entities.Entity;

public interface ComponentPropertiesWrapper {

	/**
	 * Sets component's fields annotated with EntityProperty from entity properties with field's name.
	 * @param component the Component to where the fields are going to be set.
	 * @param entity the Entity from where to get the properties values.
	 */
	void importFrom(Component component, Entity entity);

	/**
	 * Sets Entity properties from Component fields with property's name, annotated with EntityPropety. 
	 * @param component the Component from the field values are obtained.
	 * @param entity the Entity to where the properties are going to be set.
	 */
	void exportTo(Component component, Entity entity);

}