package com.gemserk.componentsengine.reflection.wrapper;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.properties.PropertiesHolder;
import com.gemserk.componentsengine.properties.ReferenceProperty;
import com.gemserk.componentsengine.reflection.internalfields.InternalField;

public class ReferencePropertyWrapper {

	protected static final Logger logger = LoggerFactory.getLogger(ReferencePropertyWrapper.class);

	PropertyWithField[] propertiesWithField;

	public static class PropertyWithField {
		final ReferenceProperty entityPropertyReference;
		final InternalField internalField;

		public PropertyWithField(ReferenceProperty entityPropertyReference, InternalField internalField) {
			this.entityPropertyReference = entityPropertyReference;
			this.internalField = internalField;
		}

	}

	public ReferencePropertyWrapper(String scopeId, Collection<InternalField> internalFields) {
		propertiesWithField = new PropertyWithField[internalFields.size()];
		int index = 0;
		for (InternalField internalField : internalFields) {
			String fieldName = internalField.getFieldName();
			
			String propertyName = scopeId != null ? scopeId + "." + fieldName : fieldName;

			propertiesWithField[index] = new PropertyWithField(new ReferenceProperty(propertyName.intern()), internalField);
			index++;
		}
	}

	public void config(Object component) {

		for (PropertyWithField propertyWithField : propertiesWithField) {

			propertyWithField.internalField.setValue(component, propertyWithField.entityPropertyReference);
		}
	}

	public void wrap(PropertiesHolder propertiesHolder) {
		for (PropertyWithField propertyWithField : propertiesWithField) {
			propertyWithField.entityPropertyReference.setPropertiesHolder(propertiesHolder);
		}
	}

}
