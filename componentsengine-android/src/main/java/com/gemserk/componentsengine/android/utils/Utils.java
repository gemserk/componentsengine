package com.gemserk.componentsengine.android.utils;

import java.util.Random;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import android.graphics.RectF;

import com.gemserk.componentsengine.utils.Container;
import com.gemserk.componentsengine.utils.Interval;

public class Utils {
	Random random = new Random();
	
	public Vector2f vector(float x, float y) {
		return new Vector2f(x, y);
	}

	public Vector2f randomVector(Rectangle rectangle) {
		return randomVector(rectangle, new Vector2f());
	}
	
	public Vector2f randomVector(Rectangle rectangle, Vector2f result) {
		return result.set(rectangle.getMinX() + random.nextFloat() * rectangle.getWidth(), rectangle.getMinY() + random.nextFloat() * rectangle.getHeight());
	}

	public Interval interval(int min, int max) {
		return new Interval(min, max);
	}
	public Container container(float current, float total) {
		return new Container(current, total);
	}

	public Rectangle rectangle(float x, float y, float width, float height) {
		return new Rectangle(x, y, width, height);
	}

	public Color color(float r, float g, float b, float a) {
		return new Color(r, g, b, a);
	}

	public Color color(float r, float g, float b) {
		return new Color(r, g, b);
	}
	
	public Vector2f randomVector(RectF rectangle) {
		return new Vector2f(rectangle.left + random.nextFloat() * rectangle.width(), rectangle.top + random.nextFloat() * rectangle.height());
	}
}
