package com.gemserk.commons.slick.util;

import java.io.File;

public interface ScreenshotGrabber {
	
	void saveScreenshot(File file);
	
	/**
	 * Saves a screenshot on java.io.tmpdir using the specified prefix for the file.
	 * @param prefix
	 */
	void saveScreenshot(String prefix, String extension);
}
