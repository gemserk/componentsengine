package com.gemserk.componentsengine.render;

import java.util.LinkedList;
import java.util.List;

import groovy.lang.Closure;

import org.newdawn.slick.Graphics;

import com.google.inject.Inject;

public class Renderer {

	public static interface RenderObject {
		int getLayer();
	}

	public static class ClosureRenderObject implements RenderObject {

		Closure closure;
		int layer;

		public ClosureRenderObject(Closure closure, int layer) {
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

	public static abstract class SlickCallableRenderObject implements RenderObject {

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

	@Inject
	Graphics graphics;

	List<RenderObject> renderObjectsQueue = new LinkedList<RenderObject>();

	public void enqueue(RenderObject renderObject) {
		renderObjectsQueue.add(renderObject);
	}

	public void render() {
		for (RenderObject renderObject : renderObjectsQueue) {
			render(renderObject);
		}
		renderObjectsQueue.clear();
	}

	private void render(RenderObject renderObject) {
		if (renderObject instanceof ClosureRenderObject) {
			ClosureRenderObject closureRenderObject = (ClosureRenderObject) renderObject;
			closureRenderObject.getClosure().call(graphics);
		}
		if (renderObject instanceof SlickCallableRenderObject) {
			SlickCallableRenderObject callableRenderObject = (SlickCallableRenderObject) renderObject;
			callableRenderObject.execute(graphics);
		}
	}

}
