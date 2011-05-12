package com.gemserk.componentsengine.commons.entities.gui;

import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.commons.components.CursorOverDetector;
import com.gemserk.componentsengine.commons.components.SlickMouseHandlerComponent;
import com.gemserk.componentsengine.commons.components.SlickMouseMoveHandlerComponent;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.messages.messagebuilder.MessageBuilder;
import com.gemserk.componentsengine.templates.EntityBuilder;
import com.gemserk.componentsengine.triggers.NullTrigger;
import com.google.inject.Inject;

public class ButtonEntityBuilder extends EntityBuilder {

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

		component(new SlickMouseMoveHandlerComponent("updateCursorPosition"));

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

		component(new SlickMouseHandlerComponent("onButtonPressedReleased")).withProperties(new ComponentProperties() {
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