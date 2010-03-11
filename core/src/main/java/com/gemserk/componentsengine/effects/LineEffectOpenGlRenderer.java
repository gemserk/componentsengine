package com.gemserk.componentsengine.effects;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

public class LineEffectOpenGlRenderer {

	Color color;

	float angle;

	Vector2f translation;

	float lineWidth;

	public LineEffectOpenGlRenderer(Vector2f translation, float angle, float lineWidth, Color color) {
		this.translation = translation;
		this.angle = angle;
		this.lineWidth = lineWidth;
		this.color = color;
	}

	public void render(LineEffectParticle lineParticleEffect) {

		if (color.a <= 0.5f)
			color.a = 0.5f;
		else
			color.a = lineParticleEffect.getDeltaTime();

		glPushMatrix();
		glTranslatef(translation.x, translation.y, 0);
		glRotatef(angle, 0f, 0f, 1f);
		glLineWidth(lineWidth);
		glColor4f(color.r, color.g, color.b, color.a);

		glBegin(GL_LINES);
		{
			glVertex3f(lineParticleEffect.p1, 0, 0);
			glVertex3f(lineParticleEffect.p2, 0, 0);
		}
		glEnd();

		glPopMatrix();
	}

}