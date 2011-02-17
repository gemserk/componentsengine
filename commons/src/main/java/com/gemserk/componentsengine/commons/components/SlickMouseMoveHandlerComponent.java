package com.gemserk.componentsengine.commons.components;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.components.FieldsReflectionComponent;
import com.gemserk.componentsengine.components.annotations.EntityProperty;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.messages.Message;
import com.google.inject.Inject;

public class SlickMouseMoveHandlerComponent extends FieldsReflectionComponent {

	@EntityProperty(readOnly = true)
	Vector2f cursorPosition;

	@Inject
	Input input;

	public SlickMouseMoveHandlerComponent(String id) {
		super(id);
	}

	@Handles
	public void update(Message message) {
		cursorPosition.set(input.getMouseX(), input.getMouseY());
	}
}