package com.gemserk.componentsengine.java2d.input;

import java.awt.event.MouseEvent;

import com.gemserk.componentsengine.input.ButtonMonitor;
import com.gemserk.componentsengine.input.CoordinatesMonitor;
import com.gemserk.componentsengine.input.MonitorFactory;
import com.gemserk.componentsengine.java2d.KeyboardInput;
import com.gemserk.componentsengine.java2d.MouseInput;
import com.google.inject.Inject;

public class Java2dMonitorFactory implements MonitorFactory {
	
	@Inject
	KeyboardInput keyboardInput;
	
	@Inject
	MouseInput mouseInput;
	
	@Inject
	KeyEventCodeMapping keyEventCodeMapping;

	@Override
	public ButtonMonitor keyboardButtonMonitor(final String button) {
		return new ButtonMonitor() {
			
			@Override
			protected boolean isDown() {
				return keyboardInput.keyDown(keyEventCodeMapping.getKeyCode(button));
			}

		};
	}

	@Override
	public ButtonMonitor mouseButtonMonitor(final String button) {

		return new ButtonMonitor() {
			
			@Override
			protected boolean isDown() {
				return mouseInput.buttonDown(getButtonValue(button));
			}

			// TODO: use a map
			private int getButtonValue(String button) {
				if ("left".equalsIgnoreCase(button))
					return MouseEvent.BUTTON1;
				if ("mid".equalsIgnoreCase(button))
					return MouseEvent.BUTTON2;
				if ("right".equalsIgnoreCase(button))
					return MouseEvent.BUTTON3;
				return MouseEvent.NOBUTTON;
			}
			
		};
		
	}

	@Override
	public CoordinatesMonitor mouseCoordinatesMonitor() {
		return new CoordinatesMonitor() {
			
			@Override
			protected float readY() {
				return (float) mouseInput.getPosition().getY();
			}
			
			@Override
			protected float readX() {
				return (float) mouseInput.getPosition().getX();
			}
		};
	}

	@Override
	public CoordinatesMonitor mouseWheelMonitor() {

		return new CoordinatesMonitor() {
			
			@Override
			protected float readX() {
				return 0;
			}
			
			@Override
			protected float readY() {
				// It would be better to call it using a slick api, but there isn't a method on Input class.
				// return MouseInput.getDWheel();
				return mouseInput.getWheelRotation();
			}
			
			@Override
			public boolean hasChanged() {
				return getY() != 0f;
			}
			
		};
		
	}
	
}