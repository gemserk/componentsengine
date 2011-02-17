package com.gemserk.componentsengine.commons.entities.gui;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.commons.components.CursorOverDetector;
import com.gemserk.componentsengine.components.FieldsReflectionComponent;
import com.gemserk.componentsengine.components.annotations.EntityProperty;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.messages.messageBuilder.MessageBuilder;
import com.gemserk.componentsengine.templates.EntityBuilder;
import com.gemserk.componentsengine.triggers.NullTrigger;
import com.gemserk.componentsengine.triggers.Trigger;
import com.google.inject.Inject;

public class ButtonEntityBuilder extends EntityBuilder {

	public static class PressedReleasedComponent extends FieldsReflectionComponent {

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

		PressedReleasedComponent(String id) {
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

	@Inject
	MessageQueue messageQueue;

	@Inject
	MessageBuilder messageBuilder;

	@Override
	public void build() {

		tags("button");

		property("position", parameters.get("position"));

		property("cursorOver", false);
		property("cursorPosition", new Vector2f());

		component(new FieldsReflectionComponent("updateCursorPosition") {

			@EntityProperty(readOnly = true)
			Vector2f cursorPosition;

			@Inject
			Input input;

			@Handles
			public void update(Message message) {
				cursorPosition.set(input.getMouseX(), input.getMouseY());
			}

		});

		component(new CursorOverDetector("cursorOver")).withProperties(new ComponentProperties() {
			{
				property("bounds", parameters.get("bounds"));
				property("onEnterTrigger", parameters.get("onEnterTrigger"), new NullTrigger() {
					@Override
					public void trigger(Object... parameters) {
						messageQueue.enqueue(messageBuilder.newMessage("onButtonFocused").property("source", (Entity) parameters[0]).get());
					}
				});
				property("onLeaveTrigger", parameters.get("onLeaveTrigger"), new NullTrigger() {
					@Override
					public void trigger(Object... parameters) {
						messageQueue.enqueue(messageBuilder.newMessage("onButtonLostFocus").property("source", (Entity) parameters[0]).get());
					}
				});
			}
		});

		component(new PressedReleasedComponent("onButtonPressedReleased")).withProperties(new ComponentProperties() {
			{
				property("onPressedTrigger", parameters.get("onPressedTrigger"), new NullTrigger() {
					@Override
					public void trigger(Object... parameters) {
						messageQueue.enqueue(messageBuilder.newMessage("onButtonPressed").property("source", (Entity) parameters[0]).get());
					}
				});
				property("onReleasedTrigger", parameters.get("onReleasedTrigger"), new NullTrigger() {
					@Override
					public void trigger(Object... parameters) {
						messageQueue.enqueue(messageBuilder.newMessage("onButtonReleased").property("source", (Entity) parameters[0]).get());
					}
				});
			}
		});

	}
}