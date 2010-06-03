package com.gemserk.componentsengine.utils;

import org.newdawn.slick.util.LogSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlickToSlf4j implements LogSystem {

	static final Logger logger = LoggerFactory.getLogger(SlickToSlf4j.class);
	
	
	@Override
	public void debug(String message) {
		logger.debug(message);
	}

	@Override
	public void error(Throwable e) {
		logger.error("",e);
	}

	@Override
	public void error(String message) {
		logger.error(message);
	}

	@Override
	public void error(String message, Throwable e) {
		logger.error(message,e);
	}

	@Override
	public void info(String message) {
		logger.info(message);
	}

	@Override
	public void warn(String message) {
		logger.warn(message);
	}

}
