package com.gemserk.componentsengine.properties;

public class Properties {

	public static <T> PropertyLocator<T> property(String key) {
		return new PropertyLocator<T>(key);
	}

	public static <T> PropertyLocator<T> property(String prefix, String key) {
		return new PropertyLocator<T>(prefix + "." + key);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getValue(PropertiesHolder propertiesHolder, String key) {
		return (T) property(key).getValue(propertiesHolder);
	}

	public static void setValue(PropertiesHolder propertiesHolder, String key, Object value) {
		property(key).setValue(propertiesHolder, value);
	}

}
