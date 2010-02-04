package com.gemserk.componentsengine.templates;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class GroovyTemplateProvider implements TemplateProvider {

	GroovyScriptProvider groovyScriptProvider;

	Injector injector;

	@Inject
	public void setInjector(Injector injector) {
		this.injector = injector;
	}

	@Inject
	public void setGroovyScriptProvider(GroovyScriptProvider groovyScriptProvider) {
		this.groovyScriptProvider = groovyScriptProvider;
	}

	@Override
	public EntityTemplate getTemplate(String name) {

		try {
			return new GroovyEntityTemplate(groovyScriptProvider.load(name), injector);
		} catch (Exception e) {
			throw new TemplateNotFoundException("Unable to load template", e);
		}
	}

}
