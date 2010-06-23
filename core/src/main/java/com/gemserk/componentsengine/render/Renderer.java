package com.gemserk.componentsengine.render;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.newdawn.slick.Graphics;

import com.google.inject.Inject;

public class Renderer {

	public static class RenderObjectComparator implements Comparator<RenderObject> {
		@Override
		public int compare(RenderObject o1, RenderObject o2) {
			return o1.getLayer() - o2.getLayer();
		}
	}

	@Inject
	Graphics graphics;

	List<RenderObject> renderObjectsQueue = new ArrayList<RenderObject>();

	public void enqueue(RenderObject renderObject) {
		renderObjectsQueue.add(renderObject);
	}

	public void render() {
		Collections.sort(renderObjectsQueue, new RenderObjectComparator());
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
