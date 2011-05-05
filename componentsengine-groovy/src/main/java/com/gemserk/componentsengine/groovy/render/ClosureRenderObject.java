package com.gemserk.componentsengine.groovy.render;

import com.gemserk.componentsengine.render.RenderObject;

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