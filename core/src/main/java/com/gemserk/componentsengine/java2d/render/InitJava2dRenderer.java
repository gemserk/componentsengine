package com.gemserk.componentsengine.java2d.render;
import com.gemserk.componentsengine.render.RenderQueueImpl;
import com.google.inject.Inject;

public class InitJava2dRenderer {
	
	@Inject RenderQueueImpl renderQueueImpl;
	
	@Inject Java2dRenderer java2dRenderer;
	
	public void config() {
		renderQueueImpl.add(java2dRenderer);
	}
	
}