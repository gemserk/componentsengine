package com.gemserk.componentsengine.utils;

import java.awt.Desktop;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DesktopUtils {

	protected static final Logger logger = LoggerFactory.getLogger(DesktopUtils.class);

	public static void openUrlInBrowser(String url) {
		
		if (!Desktop.isDesktopSupported()) {
			if (logger.isErrorEnabled())
				logger.error("Desktop feature is not supported.");
			return;
		}

		Desktop desktop = Desktop.getDesktop();

		if (!desktop.isSupported(Desktop.Action.BROWSE)) {
			if (logger.isErrorEnabled())
				logger.error("Desktop feature doesn't support the browse action.");
			return;
		}

		try {
			desktop.browse(new URI(url));
		} catch (Exception e) {
			if (logger.isErrorEnabled())
				logger.error(e.getMessage(), e);
		}
	}

}