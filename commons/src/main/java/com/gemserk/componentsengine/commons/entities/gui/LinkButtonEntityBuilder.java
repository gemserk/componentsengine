package com.gemserk.componentsengine.commons.entities.gui;

import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.commons.components.CursorOverDetector;
import com.gemserk.componentsengine.commons.components.SlickMouseHandlerComponent;
import com.gemserk.componentsengine.commons.components.SlickMouseMoveHandlerComponent;
import com.gemserk.componentsengine.templates.EntityBuilder;
import com.gemserk.componentsengine.triggers.NullTrigger;
import com.gemserk.componentsengine.triggers.OpenLinkTrigger;

public class LinkButtonEntityBuilder extends EntityBuilder {

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
				property("onEnterTrigger", parameters.get("onEnterTrigger") != null ? parameters.get("onEnterTrigger") : new NullTrigger());
				property("onLeaveTrigger", parameters.get("onLeaveTrigger") != null ? parameters.get("onLeaveTrigger") : new NullTrigger());
			}
		});

		component(new SlickMouseHandlerComponent("onLinkClickedHandler")).withProperties(new ComponentProperties() {
			{
				property("onPressedTrigger", new OpenLinkTrigger(url));
			}
		});

	}
}