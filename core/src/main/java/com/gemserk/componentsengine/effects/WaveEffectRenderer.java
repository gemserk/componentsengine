/**
 * 
 */
package com.gemserk.componentsengine.effects;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

public class WaveEffectRenderer {

	Vector2f translation = new Vector2f(0, 0);

	public float angle = 0;

	public float separation = 10;

	float lineWidth = 1.0f;

	public Color lineColor = new Color(1f, 1f, 1f, 1f);

	float pointSize = 2.0f;

	public Color pointsColor = new Color(1, 0, 0, 1);

	public WaveEffectRenderer() {

	}

	public void render(WaveParticleEffect waveEffect) {

		float medium = waveEffect.vertexCount * separation / 2f;

		glPushMatrix();
		glLineWidth(lineWidth);
		glTranslate(translation);
		glRotatef(angle, 0f, 0f, 1f);
		glBegin(GL_LINE_STRIP);
		{
			glColor(lineColor);

			for (int i = 0; i < waveEffect.vertexCount; i++) {
				glVertex2f(i * separation - medium, waveEffect.yVector[i]);
			}
		}
		glEnd();
		glPopMatrix();

		glPushMatrix();
		glPointSize(pointSize);
		glTranslate(translation);
		glRotatef(angle, 0f, 0f, 1f);
		glBegin(GL_POINTS);
		{
			glColor(pointsColor);

			for (int i = 0; i < waveEffect.vertexCount; i++) {
				glVertex2f(i * separation - medium, waveEffect.yVector[i]);
			}
		}
		glEnd();

		glPopMatrix();

	}

	private void glColor(Color c) {
		glColor4f(c.r, c.g, c.b, c.a);
	}

	private void glTranslate(Vector2f p) {
		glTranslatef(p.x, p.y, 0);
	}

}