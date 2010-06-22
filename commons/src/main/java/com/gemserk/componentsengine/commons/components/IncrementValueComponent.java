package com.gemserk.componentsengine.commons.components;

import com.gemserk.componentsengine.annotations.EntityProperty;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.Properties;

public class IncrementValueComponent extends FieldsReflectionComponent {

	@EntityProperty
	Float value;

	@EntityProperty(readOnly=true)
	Float maxValue;

	@EntityProperty(readOnly=true)
	Float increment;
	
	@EntityProperty(readOnly=true, required=false)
	Boolean loop = true;

	public IncrementValueComponent(String id) {
		super(id);
	}

	@Handles
	public void update(Message message) {
		int delta = (Integer) Properties.getValue(message, "delta");
		
		value += increment *delta;
		if (value > maxValue){
			value = loop ? value - maxValue : maxValue;
		}
		
	}
	
}