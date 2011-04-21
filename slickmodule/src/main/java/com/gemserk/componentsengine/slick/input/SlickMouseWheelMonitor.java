package com.gemserk.componentsengine.slick.input;

import org.lwjgl.input.Mouse;

import com.gemserk.componentsengine.input.CoordinatesMonitor;

public class SlickMouseWheelMonitor extends CoordinatesMonitor {
	@Override
	protected float readX() {
		return 0f;
	}

	@Override
	protected float readY() {
		// It would be better to call it using a slick api, but there isn't a method on Input class.
		return Mouse.getDWheel();
	}

	@Override
	public boolean hasChanged() {
		return getY() != 0f;
	}
}