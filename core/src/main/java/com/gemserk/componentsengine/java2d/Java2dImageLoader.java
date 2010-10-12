package com.gemserk.componentsengine.java2d;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.gemserk.componentsengine.resources.images.ImageLoader;

public class Java2dImageLoader implements ImageLoader<Image> {

	@Override
	public Image load(String imagePath) {
		try {
			return ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(imagePath));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Image load(String imagePath, String alphaMaskPath) {
		throw new RuntimeException("not implemented");
	}
	
}