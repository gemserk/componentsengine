package com.gemserk.componentsengine.commons.components;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.components.FieldsReflectionComponent;
import com.gemserk.componentsengine.components.annotations.EntityProperty;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.triggers.NullTrigger;
import com.gemserk.componentsengine.triggers.Trigger;

public class CursorOverDetector extends FieldsReflectionComponent {

	@EntityProperty(readOnly = true)
	Shape bounds;

	@EntityProperty(required = false)
	Boolean cursorOver = false;

	@EntityProperty(readOnly = true, required = false)
	Trigger onEnterTrigger = new NullTrigger();

	@EntityProperty(readOnly = true, required = false)
	Trigger onLeaveTrigger = new NullTrigger();

	@EntityProperty(readOnly = true)
	Vector2f position;

	@EntityProperty(readOnly = true)
	Vector2f cursorPosition;

	public CursorOverDetector(String id) {
		super(id);
	}

	@Handles
	public void update(Message message) {
		boolean newCursorOver = bounds.contains(cursorPosition.x - position.x, cursorPosition.y - position.y);

		if (cursorOver)
			if (!newCursorOver)
				onLeaveTrigger.trigger(entity);

		if (!cursorOver)
			if (newCursorOver)
				onEnterTrigger.trigger(entity);

		cursorOver = newCursorOver;
	}

}