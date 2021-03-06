package com.gemserk.componentsengine.slick.effects;


import static com.gemserk.componentsengine.lwjgl.OpenGlUtils.glVertex;
import static org.lwjgl.opengl.GL11.*;

import java.util.Collection;

import org.newdawn.slick.geom.Line;

public class LightingBoltEffect {

	Collection<Line> segments;

	int totalTime;

	int currentTime;

	private float lineWidth;

	public LightingBoltEffect(int time, Collection<Line> segments, float lineWidth) {
		this.totalTime = time;
		this.segments = segments;
		this.currentTime = time;
		this.lineWidth = lineWidth;
	}

	public void update(int delta) {

		currentTime -= delta;

		if (currentTime <= 0)
			currentTime = 0;

	}

	public void render() {

		float alpha = (float) currentTime / (float) totalTime;

		glPushMatrix();
		glColor4f(alpha, alpha, alpha, alpha);
		glLineWidth(lineWidth);

		glBegin(GL_LINES);
		{
			for (Line segment : segments) {
				glVertex(segment.getStart());
				glVertex(segment.getEnd());
			}
		}
		glEnd();

		glPopMatrix();
	}

	public boolean isDone() {
		return currentTime <= 0;
	}
}