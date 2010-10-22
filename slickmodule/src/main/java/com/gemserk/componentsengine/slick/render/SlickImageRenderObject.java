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

	private float w;

	private float h;

	private Image image;
	
	public SlickImageRenderObject() {
		super(0);
	}

	public SlickImageRenderObject(int layer, Image image, Vector2f position, Vector2f size, float theta, Color color) {
		this(layer, image, position.x, position.y, size.x, size.y, theta, color);
	}

	public SlickImageRenderObject(int layer, Image image, float x, float y, Vector2f size, float theta, Color color) {
		this(layer, image, x, y, size.x, size.y, theta, color);
	}

	public SlickImageRenderObject(int layer, Image image, float x, float y, float w, float h, float theta, Color color) {
		super(layer);
		set(layer, image, x, y, w, h, theta, color);
	}
	
	public void set(int layer, Image image, float x, float y, float w, float h, float theta, Color color) {
		this.layer = layer;
		this.color = color;
		this.theta = theta;
		this.x = x;
		this.y = y;
		this.image = image;
		this.w = w;
		this.h = h;
	}

	@Override
	public void execute(Graphics g) {
		g.pushTransform();
		{
			g.translate(x, y);
			g.scale(w, h);
			g.rotate(0, 0, theta);
			g.drawImage(image, -(image.getWidth() / 2), -(image.getHeight() / 2), color);
		}
		g.popTransform();
	}
}