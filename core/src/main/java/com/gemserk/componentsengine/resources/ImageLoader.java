package com.gemserk.componentsengine.resources;

public interface ImageLoader<T> {
	
	T load(String imagePath);

	T load(String imagePath, String alphaMaskPath);
	
}