package com.gemserk.componentsengine.reflection.wrapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.components.annotations.EntityProperty;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.properties.Property;
import com.gemserk.componentsengine.reflection.RequiredPropertyNotFoundException;
import com.google.inject.internal.Strings;

public class ComponentPropertiesWrapperImpl implements ComponentPropertiesWrapper {

	protected static final Logger logger = LoggerFactory.getLogger(ComponentPropertiesWrapperImpl.class);

	protected final Class<? extends Component> componentClass;

	protected Collection<InternalField> internalFields = new LinkedList<InternalField>();

	public static boolean useFastClassIfPossible = true;

	public ComponentPropertiesWrapperImpl(Class<? extends Component> componentClass) {
		this.componentClass = componentClass;

		List<Field> listFields = new ArrayList<Field>();

		Field[] fields = componentClass.getDeclaredFields();

		for (Field field : fields) {

			if (!field.isAnnotationPresent(EntityProperty.class))
				continue;

			internalFields.add(generateInternalField(field));

			listFields.add(field);
		}

	}

	protected InternalField generateInternalField(Field field) {

		// TODO: check first if field is public

		if (ComponentPropertiesWrapperImpl.useFastClassIfPossible) {
			try {
				return buildInternalFieldFastClassImpl(field);
			} catch (NoSuchMethodError e) {
				return buildInternalFieldPublicImpl(field);
			}
		} else {

			try {
				return buildInternalFieldMethodsImpl(field);
			} catch (Exception e) {
				logger.debug(e.getMessage());
			}

			// try before using methods but without fast class...
			return buildInternalFieldPublicImpl(field);
		}

	}

	private InternalField buildInternalFieldMethodsImpl(Field field) {

		String fieldName = field.getName();

		String setterName = getSetterName(fieldName);
		String getterName = getGetterName(fieldName);

		Method[] methods = componentClass.getMethods();

		Method setterMethod = null;
		Method getterMethod = null;

		for (Method method : methods) {
			if (method.getName().equals(setterName))
				setterMethod = method;
			else if (method.getName().equals(getterName))
				getterMethod = method;
		}

		if (setterMethod == null || getterMethod == null)
			throw new RuntimeException("failed to retrieve getter and setter methods for " + fieldName);

		return new InternalFieldMethodsReflectionImpl(field, getterMethod, setterMethod);

	}

	private InternalField buildInternalFieldFastClassImpl(Field field) {
		String fieldName = field.getName();
		FastClass componentFastClass = FastClass.create(componentClass);

		// TODO: separate read access from write access, maybe we can have a setter but not a getter and vice versa.

		FastMethod setterFastMethod = componentFastClass.getMethod(getSetterName(fieldName), new Class[] { field.getType() });
		FastMethod getterFastMethod = componentFastClass.getMethod(getGetterName(fieldName), new Class[] {});

		return new InternalFieldFastClassImpl(field, componentFastClass, // 
				setterFastMethod.getIndex(), getterFastMethod.getIndex());
	}

	private InternalField buildInternalFieldPublicImpl(Field field) {
		if (logger.isDebugEnabled())
			logger.debug("forcing field {} to be accesible because missing getter or setter", field.getName());
		field.setAccessible(true);
		return new InternalFieldPublicImpl(field);
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
			
			if (property == null)
				continue;

			Object value = internalField.getValue(component);
			property.set(value);
		}

	}

	private String getSetterName(String fieldName) {
		return "set" + Strings.capitalize(fieldName);
	}

	private String getGetterName(String fieldName) {
		return "get" + Strings.capitalize(fieldName);
	}

	abstract class InternalField {

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
			fieldName = field.getName();
			EntityProperty entityPropertyAnnotation = field.getAnnotation(EntityProperty.class);
			required = entityPropertyAnnotation.required();
			readOnly = entityPropertyAnnotation.readOnly();
		}

		public abstract Object getValue(Object obj);

		public abstract void setValue(Object obj, Object value);

	}

	class InternalFieldMethodsReflectionImpl extends InternalField {

		Method getterMethod;

		Method setterMethod;

		public InternalFieldMethodsReflectionImpl(Field field, Method getterMethod, Method setterMethod) {
			super(field);
			this.getterMethod = getterMethod;
			this.setterMethod = setterMethod;
		}

		@Override
		public Object getValue(Object obj) {
			try {
				return getterMethod.invoke(obj, new Object[] {});
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void setValue(Object obj, Object value) {
			try {
				setterMethod.invoke(obj, value);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

	}

	class InternalFieldPublicImpl extends InternalField {

		private final Field field;

		public InternalFieldPublicImpl(Field field) {
			super(field);
			this.field = field;
		}

		@Override
		public Object getValue(Object obj) {
			try {
				return field.get(obj);
			} catch (Exception e) {
				throw new RuntimeException("field must be public", e);
			}
		}

		@Override
		public void setValue(Object obj, Object value) {
			try {
				field.set(obj, value);
			} catch (Exception e) {
				throw new RuntimeException("field must be public", e);
			}
		}

	}

	private static final Object[] DEFAULT_GETTER_ARGUMENTS = new Object[] {};

	class InternalFieldFastClassImpl extends InternalField {

		FastClass fastClass;

		int setterMethodindex;

		int getterMethodIndex;

		public InternalFieldFastClassImpl(Field field, FastClass fastClass, int setterMethodIndex, int getterMethodIndex) {
			super(field);
			this.fastClass = fastClass;
			this.setterMethodindex = setterMethodIndex;
			this.getterMethodIndex = getterMethodIndex;
		}

		@Override
		public Object getValue(Object obj) {
			try {
				return fastClass.invoke(getterMethodIndex, obj, DEFAULT_GETTER_ARGUMENTS);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}

		@Override
		public void setValue(Object obj, Object value) {
			try {
				fastClass.invoke(setterMethodindex, obj, new Object[] { value });
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}

	}

}