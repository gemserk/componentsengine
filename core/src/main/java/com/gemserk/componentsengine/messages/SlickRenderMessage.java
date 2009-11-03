package com.gemserk.componentsengine.messages;

import org.newdawn.slick.Graphics;


public class SlickRenderMessage extends Message {

	private final Graphics graphics;

	public SlickRenderMessage(Graphics graphics) {
		this.graphics = graphics;
	}

	public Graphics getGraphics() {
		return graphics;
	}
}
