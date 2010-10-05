package com.gemserk.componentsengine.groovy.utils.scripts;

import groovy.lang.GroovyClassLoader;
import groovy.lang.Script;

public class GroovyScriptProviderImpl implements GroovyScriptProvider {

	private final GroovyClassLoader groovyClassLoader;

	public GroovyScriptProviderImpl() {
		groovyClassLoader = new GroovyClassLoader();
		groovyClassLoader.setShouldRecompile(true);
	}

	public GroovyScriptProviderImpl(GroovyClassLoader groovyClassLoader) {
		this.groovyClassLoader = groovyClassLoader;
	}

	@SuppressWarnings("unchecked")
	public Class<Script> load(String name) {
		try {
			return groovyClassLoader.loadClass(name, true, false);
		} catch (Exception e) {
			throw new RuntimeException("failed to load script " + name, e);
		}
	}

}