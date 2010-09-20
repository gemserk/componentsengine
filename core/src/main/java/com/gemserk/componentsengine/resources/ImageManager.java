package com.gemserk.componentsengine.resources;


public interface ImageManager<T> {

	Resource<T> getImage(String key);

	void addImage(String key, T image);

}
