package com.gemserk.componentsengine.reflection.wrapper;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.properties.Property;
import com.gemserk.componentsengine.properties.SimpleProperty;
import com.gemserk.componentsengine.reflection.RequiredPropertyNotFoundException;
import com.gemserk.componentsengine.reflection.internalfields.InternalField;

public class FastComponentPropertiesWrapper implements ComponentPropertiesWrapper {

	protected static final Logger logger = LoggerFactory.getLogger(FastComponentPropertiesWrapper.class);


	Collection<PropertyWithField> propertiesWithField;

	public static class PropertyWithField {
		final String scopedId;
		final String globalId;
		final InternalField internalField;

		public PropertyWithField(String scopedId, String globalId, InternalField internalField) {
			this.scopedId = scopedId;
			this.globalId = globalId;
			this.internalField = internalField;
		}

	}

	public FastComponentPropertiesWrapper(String componentId, Collection<InternalField> internalFields) {
		propertiesWithField = new ArrayList<FastComponentPropertiesWrapper.PropertyWithField>(internalFields.size());
		for (InternalField internalField : internalFields) {
			String fieldName = internalField.getFieldName();
			String propertyName = componentId + "." + fieldName;

			propertiesWithField.add(new PropertyWithField(propertyName.intern(), fieldName.intern(), internalField));
		}
	}

	public void importFrom(Component component, Entity entity) {

		String componentId = component.getId();

		for (PropertyWithField propertyWithField : propertiesWithField) {
			Property<Object> property = getProperty(entity, componentId, propertyWithField);

			if (property == null)
				continue;

			Object propertyValue = property.get();
			propertyWithField.internalField.setValue(component, propertyValue);
		}
	}

	private Property<Object> getProperty(Entity entity, String componentId2, PropertyWithField propertyWithField) {

//		boolean traceEnabled = logger.isTraceEnabled();

		String scopedId = propertyWithField.scopedId;

//		if (traceEnabled) {
//			logger.trace("trying to get property " + scopedId + " from entity " + entity.getId());
//		}

		Property<Object> property = entity.getProperty(scopedId);

		if (property != null)
			return property;

//		if (traceEnabled)
//			logger.trace("property " + scopedId + " not found");

		String globalId = propertyWithField.globalId;
//		if (traceEnabled) {
//			logger.trace("trying to get property " + globalId + " from entity " + entity.getId());
//		}
		property = entity.getProperty(globalId);

		if (property != null)
			return property;

		if (propertyWithField.internalField.isRequiredProperty())
			throw new RequiredPropertyNotFoundException(globalId, "neither property " + scopedId + " nor " + globalId + " found on " + entity.getId() + "! and it is a required property");
		return null;
	}

	public void exportTo(Component component, Entity entity) {

		String componentId = component.getId();

		for (PropertyWithField propertyWithField : propertiesWithField) {
			InternalField internalField = propertyWithField.internalField;
			if (internalField.isReadOnlyProperty())
				continue;

			Property<Object> property = getProperty(entity, componentId, propertyWithField);
			Object value = internalField.getValue(component);

			if (property == null) {

				// property not found, set a new property on the entity....

				property = new SimpleProperty<Object>(value);
				entity.addProperty(componentId + "." + internalField.getFieldName(), property);
			}

			property.set(value);

		}
	}

}
