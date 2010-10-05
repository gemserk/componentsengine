package com.gemserk.componentsengine.groovy.utils.scripts;

import groovy.lang.Script;

import java.util.HashMap;
import java.util.Map;


public class CachedScriptProvider implements GroovyScriptProvider {

	GroovyScriptProvider groovyScriptProvider;

	Map<String, Class<Script>> scripts = new HashMap<String, Class<Script>>();

	public CachedScriptProvider(GroovyScriptProvider groovyScriptProvider) {
		this.groovyScriptProvider = groovyScriptProvider;
	}

	@Override
	public Class<Script> load(String name) {

		if (!scripts.containsKey(name))
			scripts.put(name, groovyScriptProvider.load(name));

		return scripts.get(name);
	}

}