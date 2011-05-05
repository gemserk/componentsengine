package com.gemserk.componentsengine.slick.input;

import org.newdawn.slick.Input;

import com.gemserk.componentsengine.input.CoordinatesMonitor;

class SlickMouseMovementMonitor extends CoordinatesMonitor {
	
	private final Input input;

	public SlickMouseMovementMonitor(Input input) {
		this.input = input;
	}
	
	@Override
	protected float readX() {
		return input.getMouseX();
	}

	@Override
	protected float readY() {
		return input.getMouseY();
	}
}