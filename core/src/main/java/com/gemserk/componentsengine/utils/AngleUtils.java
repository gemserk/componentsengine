/**
 * 
 */
package com.gemserk.componentsengine.utils;

public class AngleUtils {
	/**
	 * Returns the difference between two angles in degrees between -180 and 180
	 * @param currentAngle
	 * @param desiredAngle
	 * @return
	 */
	public double minimumDifference(double currentAngle, double desiredAngle) {
		double diffAngle = desiredAngle-currentAngle;
		
		if (diffAngle > 180)
			diffAngle -= 360;
		else if (diffAngle < -180)
			diffAngle += 360;
		return diffAngle;
	}
	
	/**
	 * Returns 
	 * @param angle
	 * @param currentAngle
	 * @param desiredAngle
	 * @return
	 */
	public double calculateTruncatedNextAngle(double angle, double currentAngle, double desiredAngle) {
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

}