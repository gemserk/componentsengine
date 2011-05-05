package com.gemserk.componentsengine.resources.images;

import java.util.HashMap;
import java.util.Map;

import com.gemserk.resources.Resource;
import com.gemserk.resources.dataloaders.StaticDataLoader;

public class ImageManagerImpl<T> implements ImageManager<T> {
	
	Map<String, Resource<T>> images = new HashMap<String, Resource<T>>();
	
	public void addImage(String key, T image){
		images.put(key, new Resource<T>(new StaticDataLoader<T>(image)));
	}
	
	public Resource<T> getImage(String key){
		return images.get(key);
	}

}
