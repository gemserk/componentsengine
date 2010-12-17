package com.gemserk.componentsengine.properties;

import com.gemserk.componentsengine.utils.Pool;
import com.gemserk.componentsengine.utils.Pool.PoolObjectFactory;

public class SimplePropertyProviderImpl implements SimplePropertyProvider {

	Pool<SimpleProperty> simplePropertyPool = new Pool<SimpleProperty>(new PoolObjectFactory<SimpleProperty>() {

		@Override
		public SimpleProperty createObject() {
			return new SimpleProperty<Object>();
		}
	},1000);
	
	
	@Override
	public SimpleProperty createProperty(Object value){
		SimpleProperty property = simplePropertyPool.newObject();
		property.set(value);
		return property;
		
	}
	
	@Override
	public void free(SimpleProperty property){
		property.set(null);
		simplePropertyPool.free(property);
	}
	
	
}
