package com.gemserk.componentsengine.templates;

import groovy.lang.GroovyClassLoader;
import groovy.lang.Script;

import com.google.inject.Inject;
import com.google.inject.Injector;

@SuppressWarnings("unchecked")
public class GroovyTemplateProvider implements TemplateProvider{

	private GroovyClassLoader groovyClassloader;
	private Injector injector;

	
	public GroovyTemplateProvider() {
		groovyClassloader = new GroovyClassLoader();
		groovyClassloader.setShouldRecompile(true);
	}
	
	
	@Inject public void setInjector(Injector injector) {
		this.injector = injector;
	}
	
	
	@Override
	public EntityTemplate getTemplate(String name) {
		Class<Script> scriptClass;
		try {
			scriptClass = (Class<Script>) groovyClassloader.loadClass(name,true,false);
			return new GroovyEntityTemplate(scriptClass,injector);
		}
		catch (Exception e) {
			throw new TemplateNotFoundException("Unable to load template",e);
		}
	}
	
	public GroovyClassLoader getGroovyClassloader() {
		return groovyClassloader;
	}

}
