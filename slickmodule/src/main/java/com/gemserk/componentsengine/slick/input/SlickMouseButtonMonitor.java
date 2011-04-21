package com.gemserk.componentsengine.slick.input;

import org.newdawn.slick.Input;

import com.gemserk.componentsengine.input.ButtonMonitor;

public class SlickMouseButtonMonitor extends ButtonMonitor {

	private final int button;

	private final Input input;

	public SlickMouseButtonMonitor(Input input, int button) {
		this.input = input;
		this.button = button;
	}

	@Override
	protected boolean isDown() {
		return input.isMouseButtonDown(button);
	}

}