package com.gemserk.componentsengine.templates;

import java.util.Map;

/**
 * Helper class to access parameters when using an entity builder.
 */
public class ParametersWrapper {

	private Map<String, Object> wrappedParameters;
	
	public void setWrappedParameters(Map<String, Object> wrappedParameters) {
		this.wrappedParameters = wrappedParameters;
	}
	
	public Map<String, Object> getWrappedParameters() {
		return wrappedParameters;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String id) {
		return (T) wrappedParameters.get(id);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String id, T defaultValue) {
		if (!wrappedParameters.containsKey(id))
			return defaultValue;
		return (T) wrappedParameters.get(id);
	}

	public void put(String key, Object value) {
		wrappedParameters.put(key, value);
	}

}