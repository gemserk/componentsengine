package com.gemserk.componentsengine.commons.components;

import java.util.List;

import org.newdawn.slick.geom.Vector2f;

public class Path {

	private List<Vector2f> points;

	private Float[] segmentDistances = null;

	public List<Vector2f> getPoints() {
		return points;
	}

	public Path(List<Vector2f> points) {
		this.points = points;
		if (points.size() == 0)
			return;
		recalculateDistances(points);
	}

	private void recalculateDistances(List<Vector2f> points) {
		segmentDistances = new Float[points.size()];
		for (int i = 0; i < points.size(); i++)
			segmentDistances[i] = getSegmentDistance(i);
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

	public float getDistanceToNextPoint(int index) {
		if (segmentDistances == null || segmentDistances.length != points.size()) {
			if (points.size() == 0 || points.size() == 1)
				return 0f;
			recalculateDistances(getPoints());
		}
		return segmentDistances[index];
	}

	private float getSegmentDistance(int index) {
		if (isLastSegment(index))
			return 0f;

		Vector2f p0 = getPoint(index);
		Vector2f p1 = getPoint(index + 1);

		return p0.distance(p1);
	}

	public boolean isLastSegment(int index) {
		return index == getPoints().size() - 1;
	}
}