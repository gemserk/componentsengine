package com.gemserk.componentsengine.slick.effects;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

public class LineEffectOpenGlRenderer {

	float angle;

	Vector2f translation;

	float lineWidth;

	public LineEffectOpenGlRenderer(Vector2f translation, float angle, float lineWidth) {
		this.translation = translation;
		this.angle = angle;
		this.lineWidth = lineWidth;
	}

	public void render(LineEffectParticle lineParticleEffect, Color color) {
		

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