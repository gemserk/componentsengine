package com.gemserk.componentsengine.timers;

public interface Timer {

	boolean update(int delta);

	void reset();
	
	boolean isRunning();
	
}