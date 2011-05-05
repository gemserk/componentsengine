package com.gemserk.commons.slick.util;

import java.io.File;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.imageout.ImageOut;

import com.google.inject.Inject;

/**
 * Utilities to save screenshots inside the game.
 * 
 * @author gemserk
 *
 */
public class SlickScreenshotGrabber implements ScreenshotGrabber {
	
	private GameContainer container;

	@Inject
	public void setContainer(GameContainer container) {
		this.container = container;
	}

	@Override
	public void saveScreenshot(File file) {
		try {
			Image image = new Image(container.getWidth(), container.getHeight());
			container.getGraphics().copyArea(image, 0, 0);
			ImageOut.write(image.getFlippedCopy(false, true), file.getAbsolutePath());
			image.destroy();
		} catch (SlickException e) {
			throw new RuntimeException("failed to save screenshot", e);
		}
	}

	@Override
	public void saveScreenshot(String prefix, String extension) {
		try {
			File tempFile = File.createTempFile(prefix, "." + extension);
			saveScreenshot(tempFile);
		} catch (IOException e) {
			throw new RuntimeException("failed to save screenshot", e);
		}
	}
	
}