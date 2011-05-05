package com.gemserk.componentsengine.java2d.render;
import com.gemserk.componentsengine.render.RenderObject;

public abstract class Java2dRenderObject implements RenderObject {

	int layer;

	public Java2dRenderObject(int layer) {
		this.layer = layer;
	}

	@Override
	public int getLayer() {
		return layer;
	}

}