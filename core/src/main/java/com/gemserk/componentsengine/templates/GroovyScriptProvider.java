package com.gemserk.componentsengine.templates;

import groovy.lang.Script;

public interface GroovyScriptProvider {

	Class<Script> load(String name);

}