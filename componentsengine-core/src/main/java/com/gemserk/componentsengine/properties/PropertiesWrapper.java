package com.gemserk.componentsengine.properties;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.reflection.internalfields.InternalField;
import com.gemserk.componentsengine.reflection.internalfields.PropertiesInternalFields;
import com.gemserk.componentsengine.reflection.wrapper.ReferencePropertyWrapper;

public class PropertiesWrapper {
	private ReferencePropertyWrapper propertiesWrapper;

	private static final Map<Class<? extends PropertiesWrapper>, Collection<InternalField>> wrappers = 
		new LinkedHashMap<Class<? extends PropertiesWrapper>, Collection<InternalField>>();

	private static final Collection<InternalField> getWrapper(Class<? extends PropertiesWrapper> clazz) {
		if (!wrappers.containsKey(clazz))
			wrappers.put(clazz, new PropertiesInternalFields(clazz).getInternalFields());
		return wrappers.get(clazz);
	}

	public PropertiesWrapper() {
		this(null);
	}
	
	public PropertiesWrapper(String id) {
		propertiesWrapper = new ReferencePropertyWrapper(id,getWrapper(getClass()));
		propertiesWrapper.config(this);
	}
	
	public void wrap(Entity entity) {
		propertiesWrapper.wrap(entity);
	}
	
	public void wrap(Message message) {
		propertiesWrapper.wrapClean(message);
	}
}
