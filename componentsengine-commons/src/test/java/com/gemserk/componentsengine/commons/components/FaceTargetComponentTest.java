package com.gemserk.componentsengine.commons.components;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;



public class FaceTargetComponentTest {
	
	private FaceTargetComponent faceTargetComponent;

	@Before
	public void setUp() {
		faceTargetComponent = new FaceTargetComponent("");
		faceTargetComponent.turnRate = 1f;
	}
	
	@Test
	public void shouldIncrementToReachDesiredAngle() {
		double calculateNextAngle = faceTargetComponent.calculateNextAngle(1, 10, 20);
		assertEquals(11, calculateNextAngle, 0.01f);
	}

	@Test
	public void shouldNotExceedDesiredAngle() {
		double calculateNextAngle = faceTargetComponent.calculateNextAngle(3, 10, 11);
		assertEquals(11, calculateNextAngle, 0.01f);
	}

	@Test
	public void shouldNotExceedDesiredAngleIsLess() {
		double calculateNextAngle = faceTargetComponent.calculateNextAngle(3, 10, 9);
		assertEquals(9, calculateNextAngle, 0.01f);
	}
	
	@Test
	public void should() {
		double calculateNextAngle = faceTargetComponent.calculateNextAngle(1, 5, 350);
		assertEquals(4, calculateNextAngle, 0.01f);
	}
	
	@Test
	public void should2() {
		double calculateNextAngle = faceTargetComponent.calculateNextAngle(1, 355, 10);
		assertEquals(356, calculateNextAngle, 0.01f);
	}
	
	@Test
	public void should3() {
		double calculateNextAngle = faceTargetComponent.calculateNextAngle(1, 0, 180);
		assertEquals(1, calculateNextAngle, 0.01f);
	}
	
	
}
