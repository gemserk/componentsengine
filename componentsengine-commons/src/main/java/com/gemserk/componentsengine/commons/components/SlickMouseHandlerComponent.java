package com.gemserk.componentsengine.commons.components;

import org.newdawn.slick.Input;

import com.gemserk.componentsengine.components.FieldsReflectionComponent;
import com.gemserk.componentsengine.components.annotations.EntityProperty;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.triggers.NullTrigger;
import com.gemserk.componentsengine.triggers.Trigger;
import com.google.inject.Inject;

public class SlickMouseHandlerComponent extends FieldsReflectionComponent {

	// TODO: use a Controller interface or something to be mouse independent or use messages generated by input monitors.

	@EntityProperty(readOnly = true)
	Boolean cursorOver;

	@Inject
	Input input;

	@EntityProperty(readOnly = true, required = false)
	Trigger onPressedTrigger = new NullTrigger();

	@EntityProperty(readOnly = true, required = false)
	Trigger onReleasedTrigger = new NullTrigger();

	@EntityProperty(required = false)
	Boolean pressed = false;

	public SlickMouseHandlerComponent(String id) {
		super(id);
	}

	@Handles
	public void update(Message message) {

		if (!cursorOver) {
			pressed = false;
			return;
		}

		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			if (!pressed) {
				onPressedTrigger.trigger(entity);
				pressed = true;
			}
		} else {
			if (pressed) {
				onReleasedTrigger.trigger(entity);
				pressed = false;
			}
		}

	}
}