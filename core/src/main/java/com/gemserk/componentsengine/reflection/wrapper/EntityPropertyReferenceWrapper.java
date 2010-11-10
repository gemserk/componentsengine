package com.gemserk.componentsengine.reflection.wrapper;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.properties.EntityPropertyReference;
import com.gemserk.componentsengine.reflection.internalfields.InternalField;

public class EntityPropertyReferenceWrapper {

	protected static final Logger logger = LoggerFactory.getLogger(EntityPropertyReferenceWrapper.class);

	PropertyWithField[] propertiesWithField;

	public static class PropertyWithField {
		final EntityPropertyReference entityPropertyReference;
		final InternalField internalField;

		public PropertyWithField(EntityPropertyReference entityPropertyReference, InternalField internalField) {
			this.entityPropertyReference = entityPropertyReference;
			this.internalField = internalField;
		}

	}

	public EntityPropertyReferenceWrapper(String componentId, Collection<InternalField> internalFields) {
		propertiesWithField = new PropertyWithField[internalFields.size()];
		int index = 0;
		for (InternalField internalField : internalFields) {
			String fieldName = internalField.getFieldName();
			String propertyName = componentId + "." + fieldName;

			propertiesWithField[index] = new PropertyWithField(new EntityPropertyReference(propertyName.intern()), internalField);
			index++;
		}
	}

	public void config(Component component) {

		for (PropertyWithField propertyWithField : propertiesWithField) {

			propertyWithField.internalField.setValue(component, propertyWithField.entityPropertyReference);
		}
	}

	public void setEntity(Entity entity) {
		for (PropertyWithField propertyWithField : propertiesWithField) {
			propertyWithField.entityPropertyReference.setEntity(entity);
		}
	}

}
