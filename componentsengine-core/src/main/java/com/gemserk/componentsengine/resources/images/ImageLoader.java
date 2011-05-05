package com.gemserk.componentsengine.resources.images;

public interface ImageLoader<T> {
	
	T load(String imagePath);

	T load(String imagePath, String alphaMaskPath);
	
}