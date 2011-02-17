package com.gemserk.componentsengine.triggers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gemserk.componentsengine.utils.DesktopUtils;

public class OpenLinkTrigger implements Trigger {

	protected static final Logger logger = LoggerFactory.getLogger(OpenLinkTrigger.class);

	private final String url;

	public OpenLinkTrigger(String url) {
		this.url = url;
	}

	@Override
	public void trigger(Object... parameters) {
		trigger();
	}

	@Override
	public void trigger() {
		DesktopUtils.openUrlInBrowser(url);
	}
}