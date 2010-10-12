package com.gemserk.componentsengine.java2d.render;
import java.awt.Graphics2D;

import com.gemserk.componentsengine.render.RenderObject;

public abstract class Java2dCallableRenderObject implements RenderObject {

	int layer;

	public abstract void execute(Graphics2D graphics, Graphics2dHelper graphicsHelper);

	public Java2dCallableRenderObject(int layer) {
		this.layer = layer;
	}

	@Override
	public int getLayer() {
		return layer;
	}

}