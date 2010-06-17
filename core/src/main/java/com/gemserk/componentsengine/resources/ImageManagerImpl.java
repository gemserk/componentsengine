package com.gemserk.componentsengine.resources;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;

public class ImageManagerImpl implements ImageManager {
	
	Map<String, Image> images = new HashMap<String, Image>();
	
	public void addImage(String key, Image image){
		images.put(key, image);
	}
	
	public Image getImage(String key){
		return images.get(key);
	}
}
