package com.gemserk.componentsengine.resources.images;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.google.inject.Inject;

@SuppressWarnings("unchecked")
public class PropertiesImageLoader {

	final String imagePropertiesFile;

	private ImageManager imageManager;
	
	private ImageLoader imageLoader;
	
	@Inject
	public void setImageLoader(ImageLoader imageLoader) {
		this.imageLoader = imageLoader;
	}

	@Inject
	public void setImageManager(ImageManager imageManager) {
		this.imageManager = imageManager;
	}

	public PropertiesImageLoader(String imagePropertiesFile) {
		this.imagePropertiesFile = imagePropertiesFile;
	}

	public void load() {
		try {
			Properties imagesMap = new Properties();
			InputStream imageMapStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(imagePropertiesFile);
			imagesMap.load(imageMapStream);

			for (String imageKey : imagesMap.stringPropertyNames()) {
				String imageProperties = imagesMap.getProperty(imageKey);
				String[] values = imageProperties.split(",");

				if (values.length == 1) 
					imageManager.addImage(imageKey, imageLoader.load(values[0]));
				 else 
					imageManager.addImage(imageKey, imageLoader.load(values[0], values[1]));
				
			}
		} catch (IOException e) {
			throw new RuntimeException("failed to load image mapping", e);
		}
	}
	

}