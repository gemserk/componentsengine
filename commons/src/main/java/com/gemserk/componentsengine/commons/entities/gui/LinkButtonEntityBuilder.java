package com.gemserk.componentsengine.commons.entities.gui;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.commons.components.CursorOverDetector;
import com.gemserk.componentsengine.components.FieldsReflectionComponent;
import com.gemserk.componentsengine.components.annotations.EntityProperty;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.templates.EntityBuilder;
import com.gemserk.componentsengine.triggers.NullTrigger;
import com.gemserk.componentsengine.triggers.OpenLinkTrigger;
import com.gemserk.componentsengine.triggers.Trigger;
import com.google.inject.Inject;

public class LinkButtonEntityBuilder extends EntityBuilder {

	public static class OnLinkClickedHandlerComponent extends FieldsReflectionComponent {
		
		@EntityProperty(readOnly = true)
		Boolean cursorOver;
		
		@Inject
		Input input;
		
		@EntityProperty(readOnly = true, required = false)
		Trigger onPressedTrigger = new NullTrigger();
		
		@EntityProperty(required = false)
		Boolean pressed = false;

		private OnLinkClickedHandlerComponent(String id) {
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
					onPressedTrigger.trigger();
					pressed = true;
				}
			} else {
				if (pressed) {
					pressed = false;
				}
			}

		}
	}

	public static class UpdateCursorPositionComponent extends FieldsReflectionComponent {
		
		@EntityProperty(readOnly = true)
		Vector2f cursorPosition;
		
		@Inject
		Input input;

		private UpdateCursorPositionComponent(String id) {
			super(id);
		}

		@Handles
		public void update(Message message) {
			cursorPosition.set(input.getMouseX(), input.getMouseY());
		}
	}

	@Override
	public void build() {

		final String url = (String) parameters.get("url");

		property("position", parameters.get("position"));
		property("cursorOver", false);

		property("cursorPosition", new Vector2f());

		component(new UpdateCursorPositionComponent("updateCursorPosition"));

		component(new CursorOverDetector("cursorOver")).withProperties(new ComponentProperties() {
			{
				property("bounds", parameters.get("bounds"));
				property("onEnterTrigger", parameters.get("onEnterTrigger") != null ? parameters.get("onEnterTrigger") : new NullTrigger());
				property("onLeaveTrigger", parameters.get("onLeaveTrigger") != null ? parameters.get("onLeaveTrigger") : new NullTrigger());
			}
		});

		component(new OnLinkClickedHandlerComponent("onLinkClickedHandler")).withProperties(new ComponentProperties() {
			{
				property("onPressedTrigger", new OpenLinkTrigger(url));
			}
		});

	}
}