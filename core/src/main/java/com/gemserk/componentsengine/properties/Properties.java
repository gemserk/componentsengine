package com.gemserk.componentsengine.properties;

public class Properties {

	public static <T> PropertyLocator<T> property(String key) {
		return new PropertyLocator<T>(key);
	}

	public static <T> PropertyLocator<T> property(String prefix, String key) {
		return new PropertyLocator<T>(prefix + "." + key);
	}

	public static <T> T getValue(PropertiesHolder propertiesHolder, String key) {
		return (T) (new PropertyLocator<T>(key)).getValue(propertiesHolder);//HACK TO FIX BUG: http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6302954
	}

	public static void setValue(PropertiesHolder propertiesHolder, String key, Object value) {
		property(key).setValue(propertiesHolder, value);
	}

}
