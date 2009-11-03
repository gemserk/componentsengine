package com.gemserk.componentsengine.templates;

import java.util.Map;

import com.gemserk.componentsengine.entities.Entity;

public interface EntityTemplate {
	
	Entity instantiate(String entityName, Map<String,Object> parameters);

	Entity apply(Entity entity, Map<String, Object> parameters);
	
}
