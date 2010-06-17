package com.gemserk.componentsengine.resources;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import com.google.inject.Inject;

public class PropertiesAnimationLoader {

	@Inject
	AnimationManager animationManager;

	public void load(String animationPropertiesFile) {
		try {
			java.util.Properties animationProperties = new java.util.Properties();
			InputStream animationInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(animationPropertiesFile);
			animationProperties.load(animationInputStream);

			for (Object keyObj : animationProperties.keySet()) {
				String key = (String) keyObj;
				String value = animationProperties.getProperty(key);

				String[] values = value.split(",");
				String file = values[0];
				int width = Integer.parseInt(values[1]);
				int height = Integer.parseInt(values[2]);
				final int time = Integer.parseInt(values[3]);
				final int framesCount = Integer.parseInt(values[4]);

				try {
					final Image image = new Image(file);
					final SpriteSheet spriteSheet = new SpriteSheet(image, width, height);
					animationManager.addAnimation(key, new AnimationInstantiator() {
						@Override
						public Animation instantiate() {
							return createAnimation(spriteSheet, framesCount, time, false);
						}
					});
				} catch (SlickException e) {
					throw new RuntimeException("failed to load animation: " + value, e);
				}

			}

		} catch (IOException e) {
			throw new RuntimeException("failed to load animations from " + animationPropertiesFile, e);
		}
	}

	/**
	 * Load an animation specifying total frames from a spritesheet.
	 * 
	 * @param time
	 * 
	 * @return
	 */
	private Animation createAnimation(SpriteSheet spriteSheet, int totalFrames, int time, boolean autoUpdate) {
		Animation animation = new Animation();
		animation.setAutoUpdate(autoUpdate);

		int horizontalCant = spriteSheet.getHorizontalCount();
		int verticalCant = spriteSheet.getVerticalCount();

		for (int j = 0; j < verticalCant; j++) {
			for (int i = 0; i < horizontalCant; i++) {
				if (i + j * horizontalCant < totalFrames) {
					animation.addFrame(spriteSheet.getSubImage(i, j), time);
				}
			}
		}

		return animation;
	}

}