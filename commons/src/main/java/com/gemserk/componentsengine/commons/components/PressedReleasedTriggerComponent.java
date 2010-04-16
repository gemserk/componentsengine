package com.gemserk.componentsengine.commons.components;

import com.gemserk.componentsengine.annotations.EntityProperty;
import com.gemserk.componentsengine.commons.components.FieldsReflectionComponent;
import com.gemserk.componentsengine.messages.GenericMessage;
import com.gemserk.componentsengine.triggers.NullTrigger;
import com.gemserk.componentsengine.triggers.Trigger;

public class PressedReleasedTriggerComponent extends FieldsReflectionComponent {

	@EntityProperty(readOnly = true, required = false)
	String pressedEvent = "mouse.leftpressed";

	@EntityProperty(readOnly = true, required = false)
	String releasedEvent = "mouse.leftreleased";

	@EntityProperty(readOnly = true, required = false)
	Trigger onPressedTrigger = new NullTrigger();

	@EntityProperty(readOnly = true, required = false)
	Trigger onReleasedTrigger = new NullTrigger();

	@EntityProperty(required = false)
	Boolean pressed = false;

	@EntityProperty(required = false)
	Boolean cursorOver = false;

	public PressedReleasedTriggerComponent(String id) {
		super(id);
	}

	public void handleMessage(GenericMessage message) {
		
		if (!cursorOver)
		{
			pressed = false;
			return;
		}
		
		if (message.getId().equals(pressedEvent)) {
			
			if (pressed)
				return;
			
			pressed = true;
			onPressedTrigger.trigger();
		}

		if (message.getId().equals(releasedEvent)) {
			
			if (!pressed)
				return;
			
			pressed = false;
			onReleasedTrigger.trigger();
		}
	}
}