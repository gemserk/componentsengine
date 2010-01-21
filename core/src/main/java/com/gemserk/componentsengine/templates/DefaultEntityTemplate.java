/**
 * 
 */
package com.gemserk.componentsengine.templates;

import java.util.Map;
import java.util.Set;

import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.properties.SimpleProperty;
import com.gemserk.componentsengine.templates.EntityTemplate;

public class DefaultEntityTemplate implements EntityTemplate {

	@Override
	public Entity apply(Entity entity) {
		return apply(entity, null);
	}

	@Override
	public Entity apply(Entity entity, Map<String, Object> parameters) {
		if (parameters != null)
			addProperties(entity, parameters);
		return entity;
	}

	@Override
	public Entity instantiate(String entityName) {
		return apply(new Entity(entityName));
	}

	@Override
	public Entity instantiate(String entityName, Map<String, Object> parameters) {
		return apply(new Entity(entityName), parameters);
	}

	private void addProperties(Entity entity, Map<String, Object> parameters) {
		Set<String> keySet = parameters.keySet();
		for (String key : keySet) {
			entity.addProperty(key, new SimpleProperty<Object>(parameters.get(key)));
		}
	}

}