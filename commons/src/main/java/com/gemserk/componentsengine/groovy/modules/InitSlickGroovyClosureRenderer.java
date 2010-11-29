package com.gemserk.componentsengine.groovy.modules;

import org.newdawn.slick.Graphics;

import com.gemserk.componentsengine.groovy.render.ClosureRenderObject;
import com.gemserk.componentsengine.render.RenderObject;
import com.gemserk.componentsengine.render.RenderQueueImpl;
import com.gemserk.componentsengine.render.Renderer;
import com.google.inject.Inject;

public class InitSlickGroovyClosureRenderer {
	@Inject
	RenderQueueImpl renderQueueImpl;

	public static class ClosureSlickRenderer implements Renderer {

		@Inject
		Graphics graphics;

		@Override
		public void render(RenderObject renderObject) {
			if (renderObject instanceof ClosureRenderObject) {
				ClosureRenderObject closureRenderObject = (ClosureRenderObject) renderObject;
				closureRenderObject.getClosure().call(graphics);
			}
		}
	}

	@Inject
	ClosureSlickRenderer closureSlickRenderer;

	public void config() {
		renderQueueImpl.add(closureSlickRenderer);
	}
}
