package com.gemserk.componentsengine.render;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RenderQueueImpl implements RenderQueue {

	public static class RenderObjectComparator implements Comparator<RenderObject> {
		@Override
		public int compare(RenderObject o1, RenderObject o2) {
			return o1.getLayer() - o2.getLayer();
		}
	}
	
	List<Renderer> renderers = new ArrayList<Renderer>();

	List<RenderObject> renderObjectsQueue = new ArrayList<RenderObject>();
	
	public void add(Renderer renderer) {
		renderers.add(renderer);
	}
	
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
		
		for (Renderer renderer : renderers) {
			renderer.render(renderObject);
		}
		
	}

}
