package com.gemserk.componentsengine.effects;

import java.util.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;

public class EffectFactory {

	public static WaveParticleEffect randomWaveEffect(int count, float maxGap, float maxAmplitude) {
		float[] xVector = new float[count];
		float[] aVector = new float[count];

		generateRandomValues(count, xVector, aVector, maxGap, maxAmplitude);

		aVector[0] = 0;
		aVector[count - 1] = 0;

		return new WaveParticleEffect(count, xVector, aVector);
	}

	public static void generateRandomValues(int vertexCount, float[] xVector, float[] aVector, float maxGap, float maxAmplitude) {
		Random random = new Random();

		for (int i = 0; i < vertexCount; i++) {
			xVector[i] = random.nextFloat() * maxGap;
			aVector[i] = random.nextFloat() * maxAmplitude;
		}
	}

	public static ExplosionEffect explosionEffect(int count, int x, int y, float minAngle, float maxAngle, int time, float length, float minDistance, float maxDistance, float width) {
		Color startColor = randomColor(color(0f, 0f, 0f, 1f), color(0.7f, 0.7f, 0.7f, 1));
		Color endColor = new Color(startColor);
		endColor.add(color(0.5f, 0.5f, 0.5f, 0.0f));
		return explosionEffect(count, x, y, minAngle, maxAngle, time, length, minDistance, maxDistance, width, startColor, endColor);
	}

	public static ExplosionEffect explosionEffect(int count, int x, int y, float minAngle, float maxAngle, int time, float length, float minDistance, float maxDistance, float width, Color startColor, Color endColor) {
		ArrayList<LineEffect> particles = new ArrayList<LineEffect>();

		for (int i = 0; i < count; i++)
			particles.add(lineEffect(x, y, minAngle, maxAngle, time, length, minDistance, maxDistance, width, new Color(startColor), new Color(endColor)));

		return new ExplosionEffect(particles);
	}

	static final Random random = new Random();

	public static Color color(float r, float g, float b, float a) {
		return new Color(r, g, b, a);
	}

	public static Color randomColor(Color minColor, Color maxColor) {

		maxColor.r -= minColor.r;
		maxColor.g -= minColor.g;
		maxColor.b -= minColor.b;
		maxColor.a -= minColor.a;

		float r = random.nextFloat() * maxColor.r + minColor.r;
		float g = random.nextFloat() * maxColor.g + minColor.g;
		float b = random.nextFloat() * maxColor.b + minColor.b;
		float a = random.nextFloat() * maxColor.a + minColor.a;

		return new Color(r, g, b, a);
	}

	public static LineEffect lineEffect(int x, int y, float minAngle, float maxAngle, int totalTime, float length, float minDistance, float maxDistance, float width, Color startColor, Color endColor) {

		maxAngle = -maxAngle;
		minAngle = -minAngle;

		float distance = random.nextFloat() * maxDistance + minDistance;
		LineEffectParticle lineParticleEffect = new LineEffectParticle(distance, totalTime, length);

		float angle = random.nextFloat() * maxAngle + minAngle;
		LineEffectOpenGlRenderer lineParticleEffectRenderer = new LineEffectOpenGlRenderer(new Vector2f(x, y), angle, width);

		LineEffect effect = new LineEffect(lineParticleEffect, lineParticleEffectRenderer, totalTime, startColor, endColor);
		return effect;
	}

	public static LightingBoltEffect lightingBoltEffect(Vector2f start, Vector2f end, int numberOfGenerations, float maxOffset, float maxAngle, double partitionProbability, int duration, float lineWidth) {

		Collection<Line> segments = new ArrayList<Line>();
		segments.add(new Line(start, end));

		float offset = maxOffset;

		Random random = new Random();

		for (int i = 0; i < numberOfGenerations; i++) {

			Collection<Line> newSegments = new ArrayList<Line>();

			for (Line segment : segments) {

				Vector2f midPoint = segment.getStart().copy().add(segment.getEnd()).scale(0.5f);

				// direction = midPoint - startPoint;
				// splitEnd = Rotate(direction, randomSmallAngle)*lengthScale + midPoint; // lengthScale is, for best results, < 1. 0.7 is a good value.
				// segmentList.Add(new Segment(midPoint, splitEnd));= 2.0f

				Vector2f perpendicular = midPoint.copy().add(90);
				perpendicular.normalise().scale(random.nextFloat() * offset - (offset / 2));
				midPoint.add(perpendicular);

				// genero con probabilidad 0.3
				if (random.nextFloat() < partitionProbability) {
					Vector2f direction = midPoint.copy().sub(segment.getStart());
					direction.add(random.nextFloat() * maxAngle);
					newSegments.add(new Line(midPoint.copy(), midPoint.copy().add(direction)));
				}

				newSegments.add(new Line(segment.getStart().copy(), midPoint.copy()));
				newSegments.add(new Line(midPoint.copy(), segment.getEnd().copy()));
			}

			segments = newSegments;

			offset /= 2;
		}

		return new LightingBoltEffect(duration, segments, lineWidth);
	}

}