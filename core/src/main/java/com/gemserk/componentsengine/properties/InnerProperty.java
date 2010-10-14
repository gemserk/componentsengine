package com.gemserk.componentsengine.properties;

import com.gemserk.componentsengine.entities.Entity;

public class InnerProperty implements Property<Object> {

	private final Entity entity;

	private final PropertyGetter propertyGetter;

	private final PropertySetter propertySetter;

	public InnerProperty(Entity entity, PropertyGetter propertyGetter, PropertySetter propertySetter) {
		this.entity = entity;
		this.propertyGetter = propertyGetter;
		this.propertySetter = propertySetter;
	}
	
	public InnerProperty(Entity entity, PropertyGetter propertyGetter) {
		this(entity, propertyGetter, null);
	}
	
	public InnerProperty(Entity entity, PropertySetter propertySetter) {
		this(entity, null, propertySetter);
	}
	
	public InnerProperty(Entity entity) {
		this(entity, null, null);
	}

	@Override
	public Object get() {
		if (propertyGetter != null)
			return propertyGetter.get(entity);
		return null;
	}

	@Override
	public void set(Object value) {
		if (propertySetter != null)
			propertySetter.set(entity, value);
	}

}