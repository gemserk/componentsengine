package com.gemserk.componentsengine.timers;



public class CountDownTimer implements Timer {

	int time;
	int timeLeft;
	boolean fired = true;

	public CountDownTimer(int time) {
		this.time = time;
		this.timeLeft = time;
	}

	public boolean update(int delta) {
		
		if(fired)
			return false;
		
		timeLeft-=delta;
		if(timeLeft<0){
			fired = true;
			return true;
		}
		return false;
	}

	public void reset() {
		timeLeft = time;
		fired = false;
	}
	
	@Override
	public String toString() {
		return String.valueOf(timeLeft);
	}
	
	public int getTimeLeft() {
		return timeLeft;
	}

	@Override
	public boolean isRunning() {
		return !fired;
	}


}
