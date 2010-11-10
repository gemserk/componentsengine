package com.gemserk.componentsengine.android.render;

import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.android.utils.Color;
import com.gemserk.componentsengine.render.RenderObject;


public class Rendereable2d implements RenderObject{
	private float angle;
	
	private Color color;

	private Sprite sprite;
	
	private Vector2f position;
	
	private Vector2f scale;

	private int layer;

	public Rendereable2d(int layer, Vector2f position, float angle, Vector2f scale, Color color, Sprite sprite) {
		set(layer, position, angle, scale, color, sprite);
	}


	public Rendereable2d set(int layer, Vector2f position, float angle, Vector2f scale, Color color, Sprite sprite) {
		this.layer = layer;
		this.position = position;
		this.angle = angle;
		this.scale = scale;
		this.color = color;
		this.sprite = sprite;
		
		return this;
	}
	

	public float getAngle() {
		return angle;
	}

	public Color getColor() {
		return color;
	}

	public Sprite getSprite() {
		return sprite;
	}
	
	public Vector2f getPosition() {
		return position;
	}
	
	public Vector2f getScale() {
		return scale;
	}

	@Override
	public int getLayer() {
		
		return layer;
	}
	
}
