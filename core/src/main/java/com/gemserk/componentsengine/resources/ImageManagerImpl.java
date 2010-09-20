package com.gemserk.componentsengine.resources;

import java.util.HashMap;
import java.util.Map;

public class ImageManagerImpl<T> implements ImageManager<T> {
	
	Map<String, Resource<T>> images = new HashMap<String, Resource<T>>();
	
	public void addImage(String key, T image){
		images.put(key, new Resource<T>(image));
	}
	
	public Resource<T> getImage(String key){
		return images.get(key);
	}

}
