package com.gemserk.componentsengine.templates;

import groovy.lang.Binding;
import groovy.lang.Script;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;

import com.gemserk.componentsengine.builders.GroovyEntityBuilder;
import com.gemserk.componentsengine.entities.Entity;
import com.google.inject.Injector;

public class GroovyEntityTemplate implements EntityTemplate {

	Class<Script> scriptClass;
	Injector injector;
	
	public GroovyEntityTemplate(Class<Script> scriptClass, Injector injector) {
		this.scriptClass = scriptClass;
		this.injector = injector;
	}

	@Override
	public Entity instantiate(String entityName) {
		return instantiate(entityName, new HashMap<String, Object>());
	}
	
	@Override
	public Entity instantiate(String entityName, Map<String, Object> parameters) {
		GroovyEntityBuilder groovyEntityBuilder = new GroovyEntityBuilder(entityName);
		injector.injectMembers(groovyEntityBuilder);
		return bindAndExecuteTemplate(entityName, groovyEntityBuilder, parameters);
	}
	
	@Override
	public Entity apply(Entity entity) {
		return apply(entity, new HashMap<String, Object>());
	}

	
	@Override
	public Entity apply(Entity entity, Map<String, Object> parameters) {
		// TODO: setear el contexto a la entity particular
		GroovyEntityBuilder groovyEntityBuilder = new GroovyEntityBuilder(entity);
		injector.injectMembers(groovyEntityBuilder);
		return bindAndExecuteTemplate(entity.getId(), groovyEntityBuilder, parameters);
	}

	private Entity bindAndExecuteTemplate(String entityName, GroovyEntityBuilder groovyEntityBuilder, Map<String, Object> parameters) {
		Binding binding = new Binding();
		binding.setVariable("entityName", entityName);
		binding.setVariable("parameters", parameters);
		binding.setVariable("log", LoggerFactory.getLogger(scriptClass.getName()));
		binding.setVariable("builder", groovyEntityBuilder);
		
		return executeTemplate(this.scriptClass,binding);
	}

	

	private Entity executeTemplate(Class<Script> scriptClass,Binding binding) {
		try {
			Script script = scriptClass.newInstance();
			script.setBinding(binding);
			return (Entity) script.run();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
