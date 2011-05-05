package com.gemserk.componentsengine.commons.path;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.commons.components.Path;

@RunWith(JMock.class)
public class PathTraversalTest {

	Mockery mockery = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	Path path;

	private ArrayList<Vector2f> points;

	@Before
	public void setUp() {
		points = new ArrayList<Vector2f>();
		path = new Path(points);
	}

	@Test
	public void ifTraversalInStartingPointWhenGoingBackwardItStaysWhereItWas() {
		Vector2f startPoint = new Vector2f(10, 10);
		points.add(startPoint);
		points.add(new Vector2f(100, 10));

		PathTraversal pathTraversal = new PathTraversal(path, 0, 0);
		assertThat(pathTraversal.getPosition(), equalTo(startPoint));
		pathTraversal = pathTraversal.add(-10);
		assertThat(pathTraversal.getPosition(), equalTo(startPoint));
	}

	@Test
	public void ifTraversalInEndingPointWhenGoingForwardItStaysWhereItWas() {
		Vector2f startPoint = new Vector2f(10, 10);
		points.add(startPoint);
		Vector2f endPoint = new Vector2f(100, 10);
		points.add(endPoint);

		PathTraversal pathTraversal = new PathTraversal(path, 1, 0);
		assertThat(pathTraversal.getPosition(), equalTo(endPoint));
		pathTraversal = pathTraversal.add(10);
		assertThat(pathTraversal.getPosition(), equalTo(endPoint));
	}

	@Test
	public void ifTraversalNearStartingPointWhenGoingBackwardItGoesToTheStartingPoint() {
		Vector2f startPoint = new Vector2f(10, 10);
		points.add(startPoint);
		points.add(new Vector2f(100, 10));

		PathTraversal pathTraversal = new PathTraversal(path, 0, 5);
		pathTraversal = pathTraversal.add(-10);
		assertThat(pathTraversal.getPosition(), equalTo(startPoint));
	}

	@Test
	public void ifTraversalNearEndingPointWhenGoingForwardItGoesToTheEndingPoint() {
		Vector2f startPoint = new Vector2f(10, 10);
		Vector2f endPoint = new Vector2f(100, 10);
		points.add(startPoint);
		points.add(endPoint);

		PathTraversal pathTraversal = new PathTraversal(path, 0, 85);
		pathTraversal = pathTraversal.add(10);
		assertThat(pathTraversal.getPosition(), equalTo(endPoint));
	}
	
	@Test
	public void forwardThreeSegments() {
		points.add(new Vector2f(10, 10));
		points.add(new Vector2f(40, 10));
		points.add(new Vector2f(40, 20));
		points.add(new Vector2f(10, 20));

		PathTraversal pathTraversal = new PathTraversal(path, 0, 0);
		pathTraversal = pathTraversal.add(15);
		assertThat(pathTraversal.getPosition(), equalTo(new Vector2f(25.0f, 10.0f)));
		pathTraversal = pathTraversal.add(16);
		assertThat(pathTraversal.getPosition(), equalTo(new Vector2f(40.0f, 11.0f)));
		pathTraversal = pathTraversal.add(10);
		assertThat(pathTraversal.getPosition(), equalTo(new Vector2f(39.0f, 20.0f)));
		pathTraversal = pathTraversal.add(100);
		assertThat(pathTraversal.getPosition(), equalTo(new Vector2f(10.0f, 20.0f)));
	}
	
	@Test
	public void forwardTwoSegmentsInOneShot() {
		points.add(new Vector2f(10, 10));
		points.add(new Vector2f(40, 10));
		points.add(new Vector2f(40, 20));
		points.add(new Vector2f(10, 20));

		PathTraversal pathTraversal = new PathTraversal(path, 0, 15);
		pathTraversal = pathTraversal.add(40);
		assertThat(pathTraversal.getPosition(), equalTo(new Vector2f(25.0f, 20.0f)));
	}
	
	@Test
	public void backwardTwoSegmentsInOneShot() {
		points.add(new Vector2f(10, 10));
		points.add(new Vector2f(40, 10));
		points.add(new Vector2f(40, 20));
		points.add(new Vector2f(10, 20));

		PathTraversal pathTraversal = new PathTraversal(path, 2, 15);
		pathTraversal = pathTraversal.add(-40);
		assertThat(pathTraversal.getPosition(), equalTo(new Vector2f(25.0f, 10.0f)));
	}
	
	@Test
	public void weCanGetTheTangentVectorInOnePositionInTheMiddle(){
		points.add(new Vector2f(10, 10));
		points.add(new Vector2f(40, 40));

		PathTraversal pathTraversal = new PathTraversal(path, 0, 15);
		Vector2f tangent = pathTraversal.getTangent();
		assertThat(tangent, equalTo(new Vector2f(40,40).sub(new Vector2f(10,10)).normalise()));
	}
	
	@Test
	public void weCanGetTheTangentVectorInOnePositionInTheLastPoint(){
		points.add(new Vector2f(10, 10));
		points.add(new Vector2f(40, 40));

		PathTraversal pathTraversal = new PathTraversal(path, 1, 0);
		Vector2f tangent = pathTraversal.getTangent();
		assertThat(tangent, equalTo(new Vector2f(40,40).sub(new Vector2f(10,10)).normalise()));
	}
	
	@Test
	public void withOneSegmentFirstPointIsDistanceZero(){
		points.add(new Vector2f(10, 10));
		points.add(new Vector2f(40, 40));

		PathTraversal pathTraversal = new PathTraversal(path, 0, 0);
		float distance = pathTraversal.getDistanceFromOrigin();
		assertThat(distance, equalTo(0f));
	}
	
	@Test
	public void withOneSegmentLastPointIsDistanceSegmentLength(){
		points.add(new Vector2f(10, 10));
		points.add(new Vector2f(10, 40));

		PathTraversal pathTraversal = new PathTraversal(path, 1, 0);
		float distance = pathTraversal.getDistanceFromOrigin();
		assertThat(distance, equalTo(30f));
	}
	
	@Test
	public void goingForwardWithinASegmentDistanceIsOK(){
		points.add(new Vector2f(10, 10));
		points.add(new Vector2f(10, 40));

		PathTraversal pathTraversal = new PathTraversal(path, 0, 10);
		assertThat(pathTraversal.getDistanceFromOrigin(), equalTo(10f));
		pathTraversal = pathTraversal.add(10);
		assertThat(pathTraversal.getDistanceFromOrigin(), equalTo(20f));
	}
	
	@Test
	public void goingBackWithinASegmentDistanceIsOK(){
		points.add(new Vector2f(10, 10));
		points.add(new Vector2f(10, 40));

		PathTraversal pathTraversal = new PathTraversal(path, 0, 20);
		assertThat(pathTraversal.getDistanceFromOrigin(), equalTo(20f));
		pathTraversal = pathTraversal.add(-10);
		assertThat(pathTraversal.getDistanceFromOrigin(), equalTo(10f));
	}
	
	@Test
	public void goingForwardThroughASegmentDistanceIsOK(){
		points.add(new Vector2f(10, 10));
		points.add(new Vector2f(10, 40));
		points.add(new Vector2f(40, 40));
		
		PathTraversal pathTraversal = new PathTraversal(path, 0, 10);
		assertThat(pathTraversal.getDistanceFromOrigin(), equalTo(10f));
		pathTraversal = pathTraversal.add(40);
		assertThat(pathTraversal.getDistanceFromOrigin(), equalTo(50f));
		pathTraversal = pathTraversal.add(4000);
		assertThat(pathTraversal.getDistanceFromOrigin(), equalTo(60f));
	}
	
	@Test
	public void goingBackThroughASegmentDistanceIsOK(){
		points.add(new Vector2f(10, 10));
		points.add(new Vector2f(10, 40));
		points.add(new Vector2f(40, 40));
		
		PathTraversal pathTraversal = new PathTraversal(path, 2, 0);
		assertThat(pathTraversal.getDistanceFromOrigin(), equalTo(60f));
		pathTraversal = pathTraversal.add(-40);
		assertThat(pathTraversal.getDistanceFromOrigin(), equalTo(20f));
		pathTraversal = pathTraversal.add(-4000);
		assertThat(pathTraversal.getDistanceFromOrigin(), equalTo(0f));
	}
}
