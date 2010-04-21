package com.gemserk.componentsengine.commons.components;

import com.gemserk.componentsengine.annotations.EntityProperty;
import com.gemserk.componentsengine.messages.UpdateMessage;

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

	public void handleMessage(UpdateMessage updateMessage) {
		
		value += increment * updateMessage.getDelta();
		if (value > maxValue){
			value = loop ? value - maxValue : maxValue;
		}
		
	}
	
}