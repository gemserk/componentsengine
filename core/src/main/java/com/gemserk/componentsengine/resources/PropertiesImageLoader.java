/**
 * 
 */
package com.gemserk.componentsengine.resources;

import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.gemserk.componentsengine.resources.ImageManager;
import com.google.inject.Inject;

public class PropertiesImageLoader {

	final String imagePropertiesFile;
	ImageManager imageManager;
	
	@Inject
	public void setImageManager(ImageManager imageManager) {
		this.imageManager = imageManager;
	}

	public PropertiesImageLoader(String imagePropertiesFile) {
		this.imagePropertiesFile = imagePropertiesFile;
	}

	public void load() {
		try {
			java.util.Properties imagesMap = new java.util.Properties();
			InputStream imageMapStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(imagePropertiesFile);
			imagesMap.load(imageMapStream);

			for (String imageKey : imagesMap.stringPropertyNames()) {
				String imagePath = imagesMap.getProperty(imageKey);
				imageManager.addImage(imageKey, new Image(imagePath));
			}
		} catch (IOException e) {
			throw new RuntimeException("failed to load image mapping", e);
		} catch (SlickException e) {
			throw new RuntimeException("failed to load image", e);
		}
	}
}