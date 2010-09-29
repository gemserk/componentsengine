package com.gemserk.componentsengine.templates;
import java.util.HashMap;
import java.util.Map;

import com.gemserk.componentsengine.entities.Entity;

public class EntityTemplateAdapter implements EntityTemplate {
	
	@Override
	public Entity instantiate(String entityName, Map<String, Object> parameters) {
		return new Entity(entityName);
	}

	@Override
	public Entity instantiate(String entityName) {
		return instantiate(entityName, new HashMap<String, Object>());
	}

	@Override
	public Entity apply(Entity entity, Map<String, Object> parameters) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Entity apply(Entity entity) {
		return apply(entity, new HashMap<String, Object>());
	}
}