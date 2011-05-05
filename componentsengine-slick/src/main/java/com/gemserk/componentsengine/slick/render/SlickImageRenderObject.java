package com.gemserk.componentsengine.slick.render;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

/**
 * Temporary class while there are no custom renderers registered on the Renderer.
 */
public class SlickImageRenderObject extends SlickCallableRenderObject {

	private Color color;

	private float theta;

	private float x;

	private float y;

	private float sx;

	private float sy;

	private Image image;
	
	public SlickImageRenderObject() {
		super(0);
	}

	public SlickImageRenderObject(int layer, Image image, Vector2f position, Vector2f scale, float theta, Color color) {
		this(layer, image, position.x, position.y, scale.x, scale.y, theta, color);
	}

	public SlickImageRenderObject(int layer, Image image, float x, float y, Vector2f scale, float theta, Color color) {
		this(layer, image, x, y, scale.x, scale.y, theta, color);
	}

	public SlickImageRenderObject(int layer, Image image, float x, float y, float sx, float sy, float theta, Color color) {
		super(layer);
		set(layer, image, x, y, sx, sy, theta, color);
	}
	
	public void set(int layer, Image image, float x, float y, float sx, float sy, float theta, Color color) {
		this.layer = layer;
		this.color = color;
		this.theta = theta;
		this.x = x;
		this.y = y;
		this.image = image;
		this.sx = sx;
		this.sy = sy;
	}

	@Override
	public void execute(Graphics g) {
		g.pushTransform();
		{
			g.translate(x, y);
			g.scale(sx, sy);
			g.rotate(0, 0, theta);
			g.drawImage(image, -(image.getWidth() / 2), -(image.getHeight() / 2), color);
		}
		g.popTransform();
	}
}