package com.gemserk.componentsengine.effects;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.utils.OpenGlUtils;

public class BeamEffect {

	int currentTime;

	int totalTime;

	public void update(int delta) {

		currentTime -= delta;

		if (currentTime <= 0)
			currentTime = 0;

		// custom

		float time = (float) currentTime / (float) totalTime;
		// color.a = time;

		if (currentTime > totalTime / 2)
			currentWidth = startWidth * time + endWidth * (1f - time);
		else
			currentWidth = endWidth * time + startWidth * (1f - time);

	}

	public boolean isDone() {
		return currentTime <= 0;
	}

	// /

	Vector2f start;

	Vector2f end;

	private float startWidth;

	private float currentWidth;

	private float endWidth;

	Color color;

	public BeamEffect(int totalTime, Vector2f start, Vector2f end, float startWidth, float endWidth, Color color) {
		this.totalTime = totalTime;
		this.currentTime = totalTime;
		this.start = start;
		this.end = end;
		this.startWidth = startWidth;
		this.endWidth = endWidth;

		currentWidth = startWidth;
		
		this.color = color;
	}

	public void render() {

		if (isDone())
			return;

		// custom

		renderRectangle(start, end, currentWidth, color);

	}

	public void renderRectangle(Vector2f start, Vector2f end, float width, Color color) {

		Vector2f difference = end.copy().sub(start);

		float length = difference.length();
		float angle = (float) difference.getTheta();

		Vector2f midpoint = difference.copy().scale(0.5f).add(start);

		OpenGlUtils.renderPolygon(midpoint, length, 1f, width, angle, color);

	}

}