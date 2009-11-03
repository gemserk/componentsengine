package com.gemserk.componentsengine.resources;

import org.newdawn.slick.Image;

public interface ImageManager {

	Image getImage(String key);

	void addImage(String key, Image image);

}
