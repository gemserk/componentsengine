package com.gemserk.componentsengine.properties;

public class Properties {

	public static <T> PropertyLocator<T> property(String key){
		return new PropertyLocator<T>(key);
	}
}
