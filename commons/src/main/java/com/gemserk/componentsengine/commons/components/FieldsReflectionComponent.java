package com.gemserk.componentsengine.commons.components;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.components.ReflectionComponent;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.reflection.wrapper.ComponentPropertiesWrapper;
import com.gemserk.componentsengine.reflection.wrapper.ComponentPropertiesWrapperImpl;

public class FieldsReflectionComponent extends ReflectionComponent {

	private ComponentPropertiesWrapper propertiesWrapper;

	// TODO: caching de los wrappers por clase

	// @SuppressWarnings("unchecked")
	// private static final Map<Class, ComponentPropertiesWrapper> wrappers = new
	// LinkedHashMap<Class, ComponentPropertiesWrapper>();

	private static final ComponentPropertiesWrapper getWrapper(Component component) {
		// if (!wrappers.containsKey(component.getClass()))
		// wrappers.put(component.getClass(), new
		// ComponentPropertiesWrapperImpl(component));
		return new ComponentPropertiesWrapperImpl(component);
	}

	public FieldsReflectionComponent(String id) {
		super(id);
		propertiesWrapper = getWrapper(this);
	}

	@Override
	public void handleMessage(Message message) {
		propertiesWrapper.importFrom(entity);
		super.handleMessage(message);
		propertiesWrapper.exportTo(entity);
	}

}
