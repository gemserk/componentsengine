package com.gemserk.componentsengine.reflection.wrapper;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gemserk.componentsengine.properties.PropertiesHolder;
import com.gemserk.componentsengine.properties.ReferenceProperty;
import com.gemserk.componentsengine.properties.ReferencePropertyWithFallback;
import com.gemserk.componentsengine.reflection.internalfields.InternalField;

public class ReferencePropertyWrapper {

	protected static final Logger logger = LoggerFactory.getLogger(ReferencePropertyWrapper.class);

	PropertyWithField[] propertiesWithField;

	PropertiesHolder currentlyWrappedPropertiesPlaceholder;
	
	
	public static class PropertyWithField {
		final ReferencePropertyWithFallback entityPropertyReference;
		final InternalField internalField;

		public PropertyWithField(ReferencePropertyWithFallback entityPropertyReference, InternalField internalField) {
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
			String fallbackPropertyName = fieldName;

			propertiesWithField[index] = new PropertyWithField(new ReferencePropertyWithFallback(propertyName.intern(),fallbackPropertyName.intern()), internalField);
			index++;
		}
	}

	public void config(Object component) {

		for (PropertyWithField propertyWithField : propertiesWithField) {

			propertyWithField.internalField.setValue(component, propertyWithField.entityPropertyReference);
		}
	}

	public void wrap(PropertiesHolder propertiesHolder) {
		if(currentlyWrappedPropertiesPlaceholder == propertiesHolder)
			return;
		
		wrapClean(propertiesHolder);
	}
	
	public void wrapClean(PropertiesHolder propertiesHolder) {	
		currentlyWrappedPropertiesPlaceholder = propertiesHolder;
		
		for (PropertyWithField propertyWithField : propertiesWithField) {
			propertyWithField.entityPropertyReference.setPropertiesHolder(propertiesHolder);
		}
	}

}
