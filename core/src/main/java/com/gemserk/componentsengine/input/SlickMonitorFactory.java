/**
 * 
 */
package com.gemserk.componentsengine.input;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Input;

import com.google.inject.Inject;

public class SlickMonitorFactory implements MonitorFactory{

	Input input;

	@Inject
	public void setInput(Input input) {
		this.input = input;
	}
	
	
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
	
	public int mapKeyboardButton(String button){
		String buttonName = button.toUpperCase();
		return Keyboard.getKeyIndex(buttonName);
	}
	

	
	public ButtonMonitor keyboardButtonMonitor(String button){
		final int keyboardButton = mapKeyboardButton(button);
		return new ButtonMonitor() {

			@Override
			protected boolean isDown() {
				return input.isKeyDown(keyboardButton);
			}
		};
	}

	public ButtonMonitor mouseButtonMonitor(String button) {

		final int mouseButton = mapMouseButton(button);
		
		return new ButtonMonitor(){

			@Override
			protected boolean isDown() {
				return input.isMouseButtonDown(mouseButton);
			}
		};
	}

	public CoordinatesMonitor mouseCoordinatesMonitor() {
		return new CoordinatesMonitor() {

			@Override
			protected float readX() {
				return input.getMouseX();
			}

			@Override
			protected float readY() {
				return input.getMouseY();
			}
		};
	}

}