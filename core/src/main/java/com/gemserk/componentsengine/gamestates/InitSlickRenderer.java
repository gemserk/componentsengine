package com.gemserk.componentsengine.gamestates;

import com.gemserk.componentsengine.render.RenderQueueImpl;
import com.gemserk.componentsengine.slick.render.SlickRenderer;
import com.google.inject.Inject;

public class InitSlickRenderer {
	
	@Inject RenderQueueImpl renderQueueImpl;
	
	@Inject SlickRenderer slickRenderer;
	
	public void config() {
		renderQueueImpl.add(slickRenderer);
	}
	
}