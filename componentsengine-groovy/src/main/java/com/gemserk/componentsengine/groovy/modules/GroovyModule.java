package com.gemserk.componentsengine.groovy.modules;

import com.gemserk.componentsengine.groovy.templates.GroovyTemplateProvider;
import com.gemserk.componentsengine.groovy.utils.scripts.CachedScriptProvider;
import com.gemserk.componentsengine.groovy.utils.scripts.GroovyScriptProvider;
import com.gemserk.componentsengine.groovy.utils.scripts.GroovyScriptProviderImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class GroovyModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(GroovyScriptProvider.class).toInstance(new CachedScriptProvider(new GroovyScriptProviderImpl()));
		bind(GroovyTemplateProvider.class).in(Singleton.class);
	}

}