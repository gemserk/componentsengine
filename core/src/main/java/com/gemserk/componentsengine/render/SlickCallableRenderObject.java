package com.gemserk.componentsengine.render;

import org.newdawn.slick.Graphics;

public abstract class SlickCallableRenderObject implements RenderObject {

	int layer;

	public abstract void execute(Graphics graphics);

	public SlickCallableRenderObject(int layer) {
		this.layer = layer;
	}

	@Override
	public int getLayer() {
		return layer;
	}

}