package com.gemserk.componentsengine.reflection.internalfields;

import java.lang.reflect.Field;

import com.gemserk.componentsengine.components.annotations.EntityProperty;

public abstract class InternalField {

	private final String fieldName;

	private final boolean required;

	private boolean readOnly;

	public String getFieldName() {
		return fieldName;
	}

	public boolean isRequiredProperty() {
		return required;
	}

	public boolean isReadOnlyProperty() {
		return readOnly;
	}

	public InternalField(Field field) {
		EntityProperty entityPropertyAnnotation = field.getAnnotation(EntityProperty.class);
		
		String key = entityPropertyAnnotation.key();
		fieldName = key.equals("") ? field.getName() : key;
		required = entityPropertyAnnotation.required();
		readOnly = entityPropertyAnnotation.readOnly();
	}

	public abstract Object getValue(Object obj);

	public abstract void setValue(Object obj, Object value);

}