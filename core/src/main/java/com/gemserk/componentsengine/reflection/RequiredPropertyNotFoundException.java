/**
 * 
 */
package com.gemserk.componentsengine.reflection;

public class RequiredPropertyNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2078009183733340974L;

	protected final String propertyName;

	public String getPropertyName() {
		return propertyName;
	}

	public RequiredPropertyNotFoundException(String propertyName, String message) {
		super(message);
		this.propertyName = propertyName;
	}

}