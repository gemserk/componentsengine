package com.gemserk.componentsengine.components;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.reflection.internalfields.InternalField;
import com.gemserk.componentsengine.reflection.internalfields.PropertiesInternalFields;
import com.gemserk.componentsengine.reflection.wrapper.EntityPropertyReferenceWrapper;

public class EntityPropertyReferenceComponent extends ReflectionComponent {

	private EntityPropertyReferenceWrapper propertiesWrapper;

	private static final Map<Class<? extends Component>, Collection<InternalField>> wrappers = 
		new LinkedHashMap<Class<? extends Component>, Collection<InternalField>>();

	private static final Collection<InternalField> getWrapper(Class<? extends Component> componentClass) {
		if (!wrappers.containsKey(componentClass))
			wrappers.put(componentClass, new PropertiesInternalFields(componentClass).getInternalFields());
		return wrappers.get(componentClass);
	}

	public EntityPropertyReferenceComponent(String id) {
		super(id);
		propertiesWrapper = new EntityPropertyReferenceWrapper(id,getWrapper(getClass()));
		propertiesWrapper.config(this);
	}
	
	@Override
	public void onAdd(Entity entity) {
		super.onAdd(entity);
		propertiesWrapper.setEntity(entity);
	}
}
