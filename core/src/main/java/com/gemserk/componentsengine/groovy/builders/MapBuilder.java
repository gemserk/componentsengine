/**
 * 
 */
package com.gemserk.componentsengine.groovy.builders;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.groovy.runtime.metaclass.MissingPropertyExceptionNoStack;

public class MapBuilder {
	Map<String, Object> innerParameters = new HashMap<String, Object>();

	public Object propertyMissing(String name) {
		Object value = innerParameters.get(name);
		if (value != null)
			return value;
		else
			throw new MissingPropertyExceptionNoStack(name,this.getClass());

	}

	public void propertyMissing(String name, Object value) {
		innerParameters.put(name, value);
	}

	public Map<String, Object> getInnerParameters() {
		return innerParameters;
	}

	public void properties(Map forcedProperties) {
		innerParameters.put("properties", forcedProperties);
	}
}