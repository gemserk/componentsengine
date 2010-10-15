package com.gemserk.componentsengine.slick.render;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

/**
 * Temporary class while there are no custom renderers registered on the Renderer.
 */
public class SlickImageRenderObject extends SlickCallableRenderObject {
	
	private final Color color;
	private final float theta;
	private final Vector2f position;
	private final Image image;
	private final Vector2f size;

	public SlickImageRenderObject(int layer, Image image, Vector2f position, Vector2f size, float theta, Color color) {
		super(layer);
		this.color = color;
		this.theta = theta;
		this.position = position;
		this.image = image;
		this.size = size;
	}
	
	public SlickImageRenderObject(int layer, Image image, float x, float y, Vector2f size, float theta, Color color) {
		this(layer, image, new Vector2f(x, y), size, theta, color);
	}
	
	public SlickImageRenderObject(int layer, Image image, float x, float y, float w, float h, float theta, Color color) {
		this(layer, image, new Vector2f(x, y), new Vector2f(w, h), theta, color);
	}

	@Override
	public void execute(Graphics g) {
		g.pushTransform();
		{
			g.translate(position.x, position.y);
			g.scale(size.x, size.y);
			g.rotate(0, 0, theta);
			g.drawImage(image, -(image.getWidth() / 2), -(image.getHeight() / 2), color);
		}
		g.popTransform();
	}
}