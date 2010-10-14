package com.gemserk.vecmath.utils;
import javax.vecmath.Vector2f;

public class VectorUtils {

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

}