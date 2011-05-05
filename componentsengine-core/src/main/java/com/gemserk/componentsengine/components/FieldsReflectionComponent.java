package com.gemserk.componentsengine.components;

import java.util.LinkedHashMap;
import java.util.Map;

import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.reflection.wrapper.ComponentPropertiesWrapper;
import com.gemserk.componentsengine.reflection.wrapper.ComponentPropertiesWrapperImpl;
import com.gemserk.componentsengine.reflection.wrapper.FastComponentPropertiesWrapper;

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
		propertiesWrapper = new FastComponentPropertiesWrapper(id,((ComponentPropertiesWrapperImpl)getWrapper(getClass())).getInternalFields());
//		propertiesWrapper = getWrapper(getClass());
	}
	
	protected void preHandleMessage(Message message) {
		propertiesWrapper.importFrom(this, entity);
	}

	protected void postHandleMessage(Message message) {
		propertiesWrapper.exportTo(this, entity);		
	}


}
