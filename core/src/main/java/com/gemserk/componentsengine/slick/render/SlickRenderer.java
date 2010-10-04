package com.gemserk.componentsengine.slick.render;

import org.newdawn.slick.Graphics;

import com.gemserk.componentsengine.groovy.render.ClosureRenderObject;
import com.gemserk.componentsengine.render.RenderObject;
import com.gemserk.componentsengine.render.Renderer;
import com.google.inject.Inject;

public class SlickRenderer implements Renderer {
	
	@Inject
	Graphics graphics;
	
	public void render(RenderObject renderObject) {
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