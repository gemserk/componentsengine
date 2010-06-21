package com.gemserk.componentsengine.messages;

import com.gemserk.componentsengine.properties.SimpleProperty;


public class UpdateMessage extends Message {

	public UpdateMessage(int delta) {
		super("update");
		addProperty("delta", new SimpleProperty<Object>(delta));
	}

}
