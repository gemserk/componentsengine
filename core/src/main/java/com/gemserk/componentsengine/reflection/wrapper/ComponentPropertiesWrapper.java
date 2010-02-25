package com.gemserk.componentsengine.reflection.wrapper;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.entities.Entity;

public interface ComponentPropertiesWrapper {

	/**
	 * Sets component's fields annotated with EntityProperty from entity properties with field's name.
	 * @param component TODO
	 */
	void importFrom(Component component, Entity entity);

	/**
	 * Sets Entity properties from Component fields with property's name, annotated with EntityPropety. 
	 * @param component TODO
	 */
	void exportTo(Component component, Entity entity);

}