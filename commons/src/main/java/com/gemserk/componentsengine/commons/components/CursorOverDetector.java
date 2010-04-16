package com.gemserk.componentsengine.commons.components;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.annotations.EntityProperty;
import com.gemserk.componentsengine.commons.components.FieldsReflectionComponent;
import com.gemserk.componentsengine.messages.GenericMessage;
import com.gemserk.componentsengine.messages.UpdateMessage;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.triggers.NullTrigger;
import com.gemserk.componentsengine.triggers.Trigger;

public class CursorOverDetector extends FieldsReflectionComponent {

	@EntityProperty(readOnly = true)
	Rectangle bounds;

	@EntityProperty(required = false)
	Boolean cursorOver = false;

	@EntityProperty(readOnly = true, required = false)
	String eventId = "mouse.move";

	@EntityProperty(readOnly = true, required = false)
	Trigger onEnterTrigger = new NullTrigger();

	@EntityProperty(readOnly = true, required = false)
	Trigger onLeaveTrigger = new NullTrigger();

	@EntityProperty(readOnly = true)
	Vector2f position;

	Vector2f cursorPosition = new Vector2f();

	public CursorOverDetector(String id) {
		super(id);
	}

	public void handleMessage(UpdateMessage message) {
		boolean newCursorOver = bounds.contains(cursorPosition.x - position.x, cursorPosition.y - position.y);

		if (cursorOver)
			if (!newCursorOver)
				onLeaveTrigger.trigger(entity);

		if (!cursorOver)
			if (newCursorOver)
				onEnterTrigger.trigger(entity);

		cursorOver = newCursorOver;
	}

	public void handleMessage(GenericMessage message) {
		if (!message.getId().equals(eventId))
			return;

		Float x = Properties.getValue(message, "x");
		Float y = Properties.getValue(message, "y");
		
		cursorPosition.set(x, y);
	}

}