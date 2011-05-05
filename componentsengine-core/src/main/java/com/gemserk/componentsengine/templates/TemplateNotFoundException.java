package com.gemserk.componentsengine.templates;

public class TemplateNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -4340970572469767159L;

	public TemplateNotFoundException(String message, Throwable cause) {
		super(message,cause);
	}

	public TemplateNotFoundException(String message) {
		super(message);
	}

}
