/**
 * 
 */
package com.gemserk.componentsengine.builders;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.groovy.runtime.metaclass.MissingPropertyExceptionNoStack;

public class MapBuilder {
	Map<String, Object> parameters = new HashMap<String, Object>();

	public Object propertyMissing(String name) {
		Object value = parameters.get(name);
		if (value != null)
			return value;
		else
			throw new MissingPropertyExceptionNoStack(name,this.getClass());

	}

	public void propertyMissing(String name, Object value) {
		parameters.put(name, value);
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void properties(Map forcedProperties) {
		parameters.put("properties", forcedProperties);
	}
}