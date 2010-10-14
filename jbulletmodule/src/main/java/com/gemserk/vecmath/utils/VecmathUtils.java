package com.gemserk.vecmath.utils;
import java.util.Random;

import javax.vecmath.Vector2f;

public class VecmathUtils {
	
	private static Random random = new Random();

	public static float getThetaInRadians(Vector2f v) {
		double theta = StrictMath.atan2(v.y, v.x);
		return getTheta(theta, Math.PI * 2);
	}

	public static float getThetaInDegrees(Vector2f v) {
		double theta = StrictMath.toDegrees(StrictMath.atan2(v.y, v.x));
		return getTheta(theta, 360.0);
	}

	private static float getTheta(double theta, double maxAngle) {
		if ((theta < -maxAngle) || (theta > maxAngle))
			theta = theta % maxAngle;
		if (theta < 0)
			theta = maxAngle + theta;
		return (float) theta;
	}

	public static Vector2f vector(float x, float y) {
		return new Vector2f(x, y);
	}
	
	public static Vector2f randomVector(float x, float y, float w, float h) {
		return vector(x + random.nextFloat() * w, y + random.nextFloat() * h);
	}
	
}