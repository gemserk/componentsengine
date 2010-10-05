package com.gemserk.componentsengine.groovy.utils.scripts;

import groovy.lang.Script;

public interface GroovyScriptProvider {

	Class<Script> load(String name);

}