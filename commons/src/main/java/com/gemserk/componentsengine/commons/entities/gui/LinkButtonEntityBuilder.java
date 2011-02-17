package com.gemserk.componentsengine.commons.entities.gui;

import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.commons.components.CursorOverDetector;
import com.gemserk.componentsengine.commons.components.SlickMouseHandlerComponent;
import com.gemserk.componentsengine.commons.components.SlickMouseMoveHandlerComponent;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.messages.messageBuilder.MessageBuilder;
import com.gemserk.componentsengine.templates.EntityBuilder;
import com.gemserk.componentsengine.triggers.NullTrigger;
import com.gemserk.componentsengine.triggers.OpenLinkTrigger;
import com.google.inject.Inject;

public class LinkButtonEntityBuilder extends EntityBuilder {

	@Inject
	MessageQueue messageQueue;

	@Inject
	MessageBuilder messageBuilder;
	
	@Override
	public void build() {

		final String url = (String) parameters.get("url");

		property("position", parameters.get("position"));
		property("cursorOver", false);

		property("cursorPosition", new Vector2f());

		component(new SlickMouseMoveHandlerComponent("updateCursorPosition"));

		component(new CursorOverDetector("cursorOver")).withProperties(new ComponentProperties() {
			{
				property("bounds", parameters.get("bounds"));
				property("onEnterTrigger", parameters.get("onEnterTrigger") != null ? parameters.get("onEnterTrigger") : new NullTrigger() {
					@Override
					public void trigger(Object... parameters) {
						messageQueue.enqueue(messageBuilder.newMessage("onCursorEnter").property("source", (Entity) parameters[0]).get());
					}
				});
				property("onLeaveTrigger", parameters.get("onLeaveTrigger") != null ? parameters.get("onLeaveTrigger") : new NullTrigger() {
					@Override
					public void trigger(Object... parameters) {
						messageQueue.enqueue(messageBuilder.newMessage("onCursorLeave").property("source", (Entity) parameters[0]).get());
					}
				});
			}
		});

		component(new SlickMouseHandlerComponent("onLinkClickedHandler")).withProperties(new ComponentProperties() {
			{
				property("onPressedTrigger", new OpenLinkTrigger(url));
			}
		});

	}
}