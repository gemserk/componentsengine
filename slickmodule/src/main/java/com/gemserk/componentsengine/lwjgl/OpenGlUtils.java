package com.gemserk.componentsengine.lwjgl;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class OpenGlUtils {

	/**
	 * Renders a rectangle of width and height centered on position rotated by angle
	 * 
	 * @param position
	 *            the center of the rectangle
	 * @param length
	 * @param width
	 * @param angle
	 * @param color
	 */
	public static void renderRectangle(Vector2f position, float length, float width, float angle, Color color) {
		renderPolygon(position, length, width, width, angle, color);
	}

	/**
	 * Renders a rectangle from start to end with the width and color specified.
	 * 
	 * @param start
	 * @param end
	 * @param width
	 * @param color
	 */
	public static void renderLine(Vector2f start, Vector2f end, float width, Color color) {

		Vector2f difference = end.copy().sub(start);

		float length = difference.length();
		float angle = (float) difference.getTheta();

		Vector2f midpoint = difference.copy().scale(0.5f).add(start);

		OpenGlUtils.renderRectangle(midpoint, length, width, angle, color);
	}

	public static void renderTrapezoid(Vector2f start, Vector2f end, float startWidth, float endWidth, Color startColor, Color endColor) {
		Vector2f difference = end.copy().sub(start);

		float length = difference.length();
		float angle = (float) difference.getTheta();

		Vector2f midpoint = difference.copy().scale(0.5f).add(start);

		renderPolygon(midpoint, length, startWidth, endWidth, angle, startColor, endColor);
	}

	public static void renderPolygon(Vector2f position, float length, float startWidth, float endWidth, float angle, Color color) {
		renderPolygon(position, length, startWidth, endWidth, angle, color, color);
	}

	public static void renderPolygon(Vector2f position, float length, float startWidth, float endWidth, float angle, Color startColor, Color endColor) {
		glPushMatrix();
		{
			glTranslatef(position.x, position.y, 0);
			glRotatef(angle, 0, 0, 1);
			glBegin(GL_QUADS);
			{
				glColor(startColor);
				glVertex3f(-length / 2, -startWidth / 2, 0);
				glColor(startColor);
				glVertex3f(-length / 2, startWidth / 2, 0);
				glColor(endColor);
				glVertex3f(length / 2, endWidth / 2, 0);
				glColor(endColor);
				glVertex3f(length / 2, -endWidth / 2, 0);
			}
			glEnd();
		}
		glPopMatrix();
	}

	public static void glColor(Color c) {
		glColor4f(c.r, c.g, c.b, c.a);
	}

	public static void glVertex(Vector2f v) {
		glVertex3f(v.x, v.y, 0);
	}

	private static final Vector2f tmpStart = new Vector2f();
	private static final Vector2f tmpEnd = new Vector2f();

	public static void renderSlickShapeBorders(Shape shape, float width, Color color) {
		
		int pointCount = shape.getPointCount();
		
		for (int i = 0; i < pointCount; i++) {
			
			float[] p0 = shape.getPoint(i);
			float[] p1 = i + 1 < pointCount ? shape.getPoint(i+1) : shape.getPoint(0);
			
			tmpStart.set(p0[0], p0[1]);
			tmpEnd.set(p1[0], p1[1]);
			
			OpenGlUtils.renderLine(tmpStart, tmpEnd, width, color);
			
		}
		
	}

}