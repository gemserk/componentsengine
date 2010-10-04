package com.gemserk.componentsengine.slick.render;

import org.newdawn.slick.Graphics;

import com.gemserk.componentsengine.render.RenderObject;

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