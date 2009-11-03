package com.gemserk.componentsengine.templates;

public class EntityTemplateNotFoundException extends RuntimeException {

	public EntityTemplateNotFoundException(String message, Throwable cause) {
		super(message,cause);
	}

	public EntityTemplateNotFoundException(String message) {
		super(message);
	}

}
