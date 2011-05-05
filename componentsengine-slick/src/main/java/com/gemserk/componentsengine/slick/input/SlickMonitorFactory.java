package com.gemserk.componentsengine.slick.input;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Input;

import com.gemserk.componentsengine.input.ButtonMonitor;
import com.gemserk.componentsengine.input.CoordinatesMonitor;
import com.gemserk.componentsengine.input.MonitorFactory;
import com.google.inject.Inject;

public class SlickMonitorFactory implements MonitorFactory {

	Input input;

	@Inject
	public void setInput(Input input) {
		this.input = input;
	}

	@SuppressWarnings("serial")
	Map<String, String> mappingMouseKeys = new HashMap<String, String>() {
		{
			put("left", "BUTTON0");
			put("right", "BUTTON1");
			put("middle", "BUTTON2");
		}
	};

	public int mapMouseButton(String key) {
		String mappedKey = mappingMouseKeys.get(key);

		if (mappedKey == null)
			mappedKey = key;

		return Mouse.getButtonIndex(mappedKey);
	}

	public int mapKeyboardButton(String button) {
		String buttonName = button.toUpperCase();
		return Keyboard.getKeyIndex(buttonName);
	}

	public ButtonMonitor keyboardButtonMonitor(String button) {
		int keyCode = mapKeyboardButton(button);
		return new SlickKeyboardButtonMonitor(input, keyCode);
	}

	public ButtonMonitor mouseButtonMonitor(String button) {
		int mouseButton = mapMouseButton(button);
		return new SlickMouseButtonMonitor(input, mouseButton);
	}

	public CoordinatesMonitor mouseCoordinatesMonitor() {
		return new SlickMouseMovementMonitor(input);
	}

	@Override
	public CoordinatesMonitor mouseWheelMonitor() {
		return new SlickMouseWheelMonitor();
	}

}