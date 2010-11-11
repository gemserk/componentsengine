package com.gemserk.componentsengine.reflection.wrapper;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.properties.Property;
import com.gemserk.componentsengine.reflection.RequiredPropertyNotFoundException;
import com.gemserk.componentsengine.reflection.internalfields.InternalField;
import com.gemserk.componentsengine.reflection.internalfields.PropertiesInternalFields;

public class ComponentPropertiesWrapperImpl implements ComponentPropertiesWrapper {

	protected static final Logger logger = LoggerFactory.getLogger(ComponentPropertiesWrapperImpl.class);

	protected final Class<? extends Component> componentClass;

	protected Collection<InternalField> internalFields;

	public ComponentPropertiesWrapperImpl(Class<? extends Component> componentClass) {
		this.componentClass = componentClass;

		PropertiesInternalFields propertiesInternalFields = new PropertiesInternalFields(componentClass);
		internalFields = propertiesInternalFields.getInternalFields();
	}

	

	public void importFrom(Component component, Entity entity) {

		String componentId = component.getId();

		for (InternalField internalField : internalFields) {

			Property<Object> property = getProperty(entity, componentId, internalField);

			if (property == null)
				continue;

			Object propertyValue = property.get();
			internalField.setValue(component, propertyValue);
		}

	}

	private Property<Object> getProperty(Entity entity, String componentId, InternalField internalField) {
		String fieldName = internalField.getFieldName();
		String propertyName = componentId + "." + fieldName;

		if (logger.isTraceEnabled())
			logger.trace("trying to get property " + propertyName + " from entity " + entity.getId());
		Property<Object> property = entity.getProperty(propertyName);

		if (property != null)
			return property;

		if (logger.isTraceEnabled())
			logger.trace("property " + propertyName + " not found");

		if (logger.isTraceEnabled())
			logger.trace("trying to get property " + fieldName + " from entity " + entity.getId());
		property = entity.getProperty(fieldName);

		if (property != null)
			return property;

		if (internalField.isRequiredProperty())
			throw new RequiredPropertyNotFoundException(fieldName, "neither property " + propertyName + " nor " + fieldName + " found on " + entity.getId() + "! and it is a required property");
		return null;
	}

	public void exportTo(Component component, Entity entity) {

		String componentId = component.getId();

		for (InternalField internalField : internalFields) {

			if (internalField.isReadOnlyProperty())
				continue;

			Property<Object> property = getProperty(entity, componentId, internalField);

			Object value = internalField.getValue(component);

			if (property == null)
				continue;

			property.set(value);
		}

	}
	
	public Collection<InternalField> getInternalFields() {
		return internalFields;
	}


}