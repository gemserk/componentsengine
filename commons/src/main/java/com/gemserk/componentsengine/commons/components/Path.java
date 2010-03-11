package com.gemserk.componentsengine.commons.components;

import java.util.List;

import org.newdawn.slick.geom.Vector2f;

public class Path {

	private List<Vector2f> points ;

	public List<Vector2f> getPoints() {
		return points;
	}
	
	public Path(List<Vector2f> points) {
		this.points = points;
	}

	public int getNextIndex(Vector2f position, int index) {
		Vector2f currentPoint = getPoint(index);
		if (currentPoint.distance(position) < 1.0f)
			return Math.min(index + 1, points.size() - 1);
		return index;
	}

	public Vector2f getPoint(int index) {
		return points.get(index);
	}

}