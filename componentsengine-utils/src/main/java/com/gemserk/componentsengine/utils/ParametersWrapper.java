package com.gemserk.componentsengine.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Lets you work with a map<string, object> in an easy way, using generic methods.
 */
public class ParametersWrapper {

	// TODO: change name to Parameters or make an interface with that name.

	private Map<String, Object> wrappedParameters;

	/**
	 * Sets the map from where the properties will be extracted.
	 * 
	 * @param wrappedParameters
	 *            The new map with properties to be extracted.
	 */
	public void setWrappedParameters(Map<String, Object> wrappedParameters) {
		this.wrappedParameters = wrappedParameters;
	}

	/**
	 * Returns the original map from where the properties are being extracted.
	 */
	public Map<String, Object> getWrappedParameters() {
		return wrappedParameters;
	}

	/**
	 * Builds a new ParametersWrapper using a new instance of a HashMap<String, Object>
	 */
	public ParametersWrapper() {
		this(new HashMap<String, Object>());
	}

	/**
	 * Builds a new ParametersWrapper using the specified map of parameters.
	 */
	public ParametersWrapper(Map<String, Object> wrappedParameters) {
		this.wrappedParameters = wrappedParameters;
	}

	/**
	 * Returns the object from the map identified by id auto casting it to specified type.
	 * 
	 * @param <T>
	 *            The type of the returned object.
	 * @param id
	 *            The identifier of the object in the map.
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String id) {
		return (T) wrappedParameters.get(id);
	}

	/**
	 * Returns the object from the map identified by id auto casting it to specified type.
	 * 
	 * @param <T>
	 *            The type of the returned object.
	 * @param id
	 *            The identifier of the object in the map.
	 * @param defaultValue
	 *            The default value of the returned object if the map doesn't contains any object identified by id.
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String id, T defaultValue) {
		if (!wrappedParameters.containsKey(id))
			return defaultValue;
		return (T) wrappedParameters.get(id);
	}

	/**
	 * Puts a new object in the map.
	 * 
	 * @param id
	 *            The identifier of the object.
	 * @param value
	 *            The object to add to the map.
	 */
	public void put(String id, Object value) {
		wrappedParameters.put(id, value);
	}

}