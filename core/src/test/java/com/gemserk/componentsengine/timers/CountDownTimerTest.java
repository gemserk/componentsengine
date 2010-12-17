package com.gemserk.componentsengine.timers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class CountDownTimerTest {

	@Test
	public void shouldStartNotStarted() {
		
		CountDownTimer countDownTimer = new CountDownTimer(1000);
		assertFalse(countDownTimer.isRunning());
		
	}
	
	@Test
	public void shouldStartStarted() {
		
		boolean started = true;
		CountDownTimer countDownTimer = new CountDownTimer(1000, started);
		assertTrue(countDownTimer.isRunning());
		
	}
	
}
