package com.gemserk.componentsengine.slick.input;

import org.newdawn.slick.Input;

import com.gemserk.componentsengine.input.ButtonMonitor;

public class SlickKeyboardButtonMonitor extends ButtonMonitor {

	private final int keyCode;

	private final Input input;

	public SlickKeyboardButtonMonitor(Input input, int keyCode) {
		this.input = input;
		this.keyCode = keyCode;
	}

	@Override
	protected boolean isDown() {
		return input.isKeyDown(keyCode);
	}

}