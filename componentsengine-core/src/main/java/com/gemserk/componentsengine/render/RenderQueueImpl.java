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
	
	private static RenderObjectComparator renderObjectComparator = new RenderObjectComparator();
	
	List<Renderer> renderers = new ArrayList<Renderer>();

	List<RenderObject> renderObjectsQueue = new ArrayList<RenderObject>();
	
	public void add(Renderer renderer) {
		renderers.add(renderer);
	}
	
	public void enqueue(RenderObject renderObject) {
		renderObjectsQueue.add(renderObject);
	}

	public void render() {
		Collections.sort(renderObjectsQueue, renderObjectComparator);
		for (int i = 0; i < renderObjectsQueue.size(); i++) {
			render(renderObjectsQueue.get(i));
		}
		renderObjectsQueue.clear();
	}

	private void render(RenderObject renderObject) {
		for (int i = 0; i < renderers.size(); i++) {
			renderers.get(i).render(renderObject);
		}
	}

}
