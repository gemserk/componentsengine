package com.gemserk.componentsengine.utils;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

public class OpenGlUtils {

	/**
	 * Renders a rectangle of width and height centered on position rotated by angle
	 * @param position the center of the rectangle
	 * @param width
	 * @param height
	 * @param angle
	 * @param color
	 */
	public static void renderRectangle(Vector2f position, float width, float height, float angle, Color color) {
		glPushMatrix();
		{
			glColor(color);
			glTranslatef(position.x, position.y, 0);
			glRotatef(angle, 0, 0, 1);
			glBegin(GL_QUADS);
			{
				glVertex3f(-width / 2, -height / 2, 0);
				glVertex3f(-width / 2, height / 2, 0);
				glVertex3f(width / 2, height / 2, 0);
				glVertex3f(width / 2, -height / 2, 0);
			}
			glEnd();
		}
		glPopMatrix();
	}

	public static void glColor(Color c) {
		glColor4f(c.r, c.g, c.b, c.a);
	}

}