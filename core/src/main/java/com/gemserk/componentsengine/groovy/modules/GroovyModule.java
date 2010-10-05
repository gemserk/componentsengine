package com.gemserk.componentsengine.groovy.modules;

import com.gemserk.componentsengine.templates.CachedScriptProvider;
import com.gemserk.componentsengine.templates.GroovyScriptProvider;
import com.gemserk.componentsengine.templates.GroovyScriptProviderImpl;
import com.gemserk.componentsengine.templates.GroovyTemplateProvider;
import com.gemserk.componentsengine.templates.TemplateProvider;
import com.google.inject.AbstractModule;

public class GroovyModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(GroovyScriptProvider.class).toInstance(new CachedScriptProvider(new GroovyScriptProviderImpl()));// GROOVY
		bind(TemplateProvider.class).toInstance(new GroovyTemplateProvider());// GROOVY
		
	}
}