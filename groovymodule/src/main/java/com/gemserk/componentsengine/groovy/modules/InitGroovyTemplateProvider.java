package com.gemserk.componentsengine.groovy.modules;

import com.gemserk.componentsengine.groovy.templates.GroovyTemplateProvider;
import com.gemserk.componentsengine.templates.TemplateProviderManager;
import com.google.inject.Inject;

public class InitGroovyTemplateProvider {

	@Inject
	TemplateProviderManager templateProviderManager;

	@Inject
	GroovyTemplateProvider groovyTemplateProvider;

	public void config() {
		templateProviderManager.register(groovyTemplateProvider);
	}

}