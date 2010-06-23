package com.gemserk.componentsengine.render;

import groovy.lang.Closure;

public class ClosureRenderObject implements RenderObject {

	Closure closure;
	int layer;

	public ClosureRenderObject(int layer, Closure closure) {
		this.closure = closure;
		this.layer = layer;
	}

	public Closure getClosure() {
		return closure;
	}

	@Override
	public int getLayer() {
		return layer;
	}

}