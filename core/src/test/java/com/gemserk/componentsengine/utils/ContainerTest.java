package com.gemserk.componentsengine.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class ContainerTest {

	@Test
	public void shouldNotSetAboveTotal() {
		
		Container container = new Container(50f, 50f);
		container.setCurrent(200f);
		assertEquals(container.getCurrent(),  container.getTotal(), 0.01f);
		
	}
	
	@Test
	public void shouldNotSetBelowZero() {
		
		Container container = new Container(50f, 50f);
		container.setCurrent(-200f);
		assertEquals(container.getCurrent(),  0f, 0.01f);
		
	}
	
}
