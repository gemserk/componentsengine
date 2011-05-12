package com.gemserk.componentsengine.utils;

/**
 * Provides common utilities when working with angles.
 */
public class AngleUtils {
	
	/**
	 * Returns the difference between two angles in degrees between -180 and 180
	 * @param currentAngle
	 * @param desiredAngle
	 * @return
	 */
	public static double minimumDifference(double currentAngle, double desiredAngle) {
		double diffAngle = desiredAngle-currentAngle;
		
		if (diffAngle > 180)
			diffAngle -= 360;
		else if (diffAngle < -180)
			diffAngle += 360;
		return diffAngle;
	}
	
	/**
	 * Returns a truncated angle.
	 * @param angle
	 * @param currentAngle
	 * @param desiredAngle
	 */
	public static double calculateTruncatedNextAngle(double angle, double currentAngle, double desiredAngle) {
		double theta = currentAngle;
		
		double diffAngle = minimumDifference(currentAngle, desiredAngle);
		
		if (diffAngle > 0)
			// turn left
			theta = currentAngle + angle;
		else 
			// turn right
			theta = currentAngle - angle;
		
		// truncate to desiredAngle
		if (desiredAngle > currentAngle && desiredAngle < theta)
			theta = desiredAngle;
		else if (desiredAngle < currentAngle && desiredAngle > theta)
			theta = desiredAngle;
		
		return theta;
	}
	
	/**
	 * Returns true whenever the angle is between minAngle and maxAngle, false otherwise.
	 * @param angle The angle to evaluate.
	 * @param minAngle The minimum angle.
	 * @param maxAngle the maximum angle.
	 */
	public static boolean between(float angle, float minAngle, float maxAngle) {
		return (AngleUtils.minimumDifference(angle, minAngle) < 0) && (AngleUtils.minimumDifference(angle, maxAngle) > 0);
	}
	
}