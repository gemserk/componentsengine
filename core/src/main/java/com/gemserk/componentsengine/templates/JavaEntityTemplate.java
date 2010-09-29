package com.gemserk.componentsengine.templates;

import java.util.Map;

import com.gemserk.componentsengine.entities.Entity;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * An EntityTemplate using EntityBuilderFactory and an EntityBuilder.
 */
public class JavaEntityTemplate extends EntityTemplateAdapter {

	private EntityBuilder entityBuilder;

	@Override
	public Entity instantiate(String entityName, Map<String, Object> parameters) {
		return apply(new Entity(entityName), parameters);
	}

	@Override
	public Entity apply(Entity entity, Map<String, Object> parameters) {

		entityBuilder.setEntity(entity);
		entityBuilder.setParameters(parameters);
		entityBuilder.build();

		return entity;
	}
	
	Injector injector;
	
	@Inject 
	public void setInjector(Injector injector) {
		this.injector = injector;
	}
	
	public JavaEntityTemplate with(EntityBuilder entityBuilder) {
		injector.injectMembers(entityBuilder);
		this.entityBuilder = entityBuilder;
		return this;
	}

}