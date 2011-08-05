package com.gemserk.componentsengine.utils;

public interface Parameters {

	/**
	 * Returns the object from the map identified by id auto casting it to specified type.
	 * 
	 * @param <T>
	 *            The type of the returned object.
	 * @param id
	 *            The identifier of the object in the map.
	 */
	<T> T get(String id);

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
	<T> T get(String id, T defaultValue);

	/**
	 * Puts a new object in the map.
	 * 
	 * @param id
	 *            The identifier of the object.
	 * @param value
	 *            The object to add to the map.
	 */
	void put(String id, Object value);

}