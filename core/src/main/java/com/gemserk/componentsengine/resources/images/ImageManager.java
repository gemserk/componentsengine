package com.gemserk.componentsengine.resources.images;

import com.gemserk.resources.Resource;

public interface ImageManager<T> {

	Resource<T> getImage(String key);

	void addImage(String key, T image);

}
