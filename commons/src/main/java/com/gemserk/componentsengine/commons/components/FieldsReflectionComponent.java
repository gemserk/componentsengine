package com.gemserk.componentsengine.commons.components;

import java.util.LinkedHashMap;
import java.util.Map;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.components.ReflectionComponent;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.reflection.wrapper.ComponentPropertiesWrapper;
import com.gemserk.componentsengine.reflection.wrapper.ComponentPropertiesWrapperImpl;

public class FieldsReflectionComponent extends ReflectionComponent {

	private ComponentPropertiesWrapper propertiesWrapper;

	private static final Map<Class<? extends Component>, ComponentPropertiesWrapper> wrappers = 
		new LinkedHashMap<Class<? extends Component>, ComponentPropertiesWrapper>();

	private static final ComponentPropertiesWrapper getWrapper(Class<? extends Component> componentClass) {
		if (!wrappers.containsKey(componentClass))
			wrappers.put(componentClass, new ComponentPropertiesWrapperImpl(componentClass));
		return wrappers.get(componentClass);
	}

	public FieldsReflectionComponent(String id) {
		super(id);
		propertiesWrapper = getWrapper(getClass());
	}
	
	protected void preHandleMessage(Message message) {
		propertiesWrapper.importFrom(this, entity);
	}

	protected void postHandleMessage(Message message) {
		propertiesWrapper.exportTo(this, entity);		
	}


}
