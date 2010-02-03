package com.gemserk.componentsengine.input;

import groovy.lang.Closure;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Input;

import com.gemserk.componentsengine.components.ReflectionComponent;
import com.gemserk.componentsengine.game.Game;
import com.gemserk.componentsengine.input.ButtonMonitor.Status;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.UpdateMessage;
import com.gemserk.componentsengine.world.World;
import com.google.inject.Inject;

public class DSLInputMapperComponent extends ReflectionComponent {

	List<Action> actions = new ArrayList<Action>();

	Map<InputKey, ButtonMonitor> buttonMonitors = new HashMap<InputKey, ButtonMonitor>();
	Map<InputKey, CoordinatesMonitor> coordinatesMonitors = new HashMap<InputKey, CoordinatesMonitor>();

	SlickMonitorFactory monitorFactory;
	
	boolean configured = false;

	private final Closure configurer;
	
	public DSLInputMapperComponent(String id, Closure configurer) {
		super(id);
		this.configurer = configurer;

	}

	public void handleMessage(UpdateMessage message) {

		if(!configured){
			configured = true;
			configurer.setDelegate(new RootNode());
			configurer.call();
		}
		
		for (Entry<InputKey, ButtonMonitor> entry : buttonMonitors.entrySet()) {
			entry.getValue().update();
		}
		for (Entry<InputKey, CoordinatesMonitor> entry : coordinatesMonitors.entrySet()) {
			entry.getValue().update();
		}

		for (Action action : actions) {
			Message newMessage = action.run(this);
			if(newMessage!=null){
				game.handleMessage(newMessage);
			}
		}
	}

	class RootNode {
		public void keyboard(Closure closure) {
			closure.setDelegate(new KeyboardNode());
			closure.call();
		}

		public void mouse(Closure closure) {
			closure.setDelegate(new MouseNode());
			closure.call();
		}
	}

	class KeyboardNode {

		public ButtonMonitor getButtonMonitor(String button) {
			InputKey inputKey = new InputKey("keyboard.", button);
			ButtonMonitor buttonMonitor = buttonMonitors.get(inputKey);
			if (buttonMonitor == null) {
				buttonMonitor = monitorFactory.keyboardButtonMonitor(button);
				buttonMonitors.put(inputKey, buttonMonitor);
			}

			return buttonMonitor;
		}

		public void press(Map<String, String> parameters) {
			press(parameters, null);
		}

		public void press(Map<String, String> parameters, Closure closure) {

			addAction(parameters, closure, ButtonMonitor.Status.PRESSED);
		}

		private void addAction(Map<String, String> parameters, Closure closure, final Status status) {
			final String button = parameters.get("button");
			String eventId = parameters.get("eventId");

			final ButtonMonitor buttonMonitor = getButtonMonitor(button);

			actions.add(new Action(button, eventId, closure) {

				@Override
				public boolean shouldRun() {
					return buttonMonitor.status(status);
				}

			});
		}

		public void hold(Map<String, String> parameters) {
			hold(parameters, null);
		}

		public void hold(Map<String, String> parameters, Closure closure) {
			addAction(parameters, closure, ButtonMonitor.Status.HOLDED);
		}

		public void release(Map<String, String> parameters) {
			release(parameters, null);
		}

		public void release(Map<String, String> parameters, Closure closure) {
			addAction(parameters, closure, ButtonMonitor.Status.RELEASED);
		}
	}

	class MouseNode {

		public ButtonMonitor getButtonMonitor(String button) {
			InputKey inputKey = new InputKey("mouse.", button);
			ButtonMonitor buttonMonitor = buttonMonitors.get(inputKey);
			if (buttonMonitor == null) {
				buttonMonitor = monitorFactory.mouseButtonMonitor(button);
				buttonMonitors.put(inputKey, buttonMonitor);
			}

			return buttonMonitor;
		}

		public CoordinatesMonitor getCoordinatesMonitor() {
			InputKey inputKey = new InputKey("mouse.move", "");
			CoordinatesMonitor coordinatesMonitor = coordinatesMonitors.get(inputKey);
			if (coordinatesMonitor == null) {
				coordinatesMonitor = monitorFactory.mouseCoordinatesMonitor();
				coordinatesMonitors.put(inputKey, coordinatesMonitor);
			}

			return coordinatesMonitor;
		}

		Map<String, String> mappingMouseKeys = new HashMap<String, String>() {
			{
				put("left", "BUTTON0");
				put("right", "BUTTON1");
				put("middle", "BUTTON2");
			}
		};

		public int mapMouseKey(String key) {
			String mappedKey = mappingMouseKeys.get(key);

			if (mappedKey == null)
				mappedKey = key;

			return Mouse.getButtonIndex(mappedKey);
		}

		public void hold(Map<String, String> parameters) {
			hold(parameters, null);
		}

		public void hold(Map<String, String> parameters, Closure closure) {

			addAction(parameters, closure, ButtonMonitor.Status.HOLDED);
		}

		public void press(Map<String, String> parameters) {
			press(parameters, null);
		}

		public void press(Map<String, String> parameters, Closure closure) {
			addAction(parameters, closure, ButtonMonitor.Status.PRESSED);
		}

		public void release(Map<String, String> parameters) {
			release(parameters, null);
		}

		public void release(Map<String, String> parameters, Closure closure) {
			addAction(parameters, closure, ButtonMonitor.Status.RELEASED);
		}

		private void addAction(Map<String, String> parameters, Closure closure, final Status status) {
			final String button = parameters.get("button");
			String eventId = parameters.get("eventId");

			final ButtonMonitor buttonMonitor = getButtonMonitor(button);

			actions.add(new Action(button, eventId, closure) {

				@Override
				public boolean shouldRun() {
					return buttonMonitor.status(status);
				}

			});
		}

		public void move(Map<String, String> parameters) {
			move(parameters, null);
		}

		public void move(Map<String, String> parameters, Closure closure) {

			final CoordinatesMonitor cordinatesMonitor = getCoordinatesMonitor();

			String eventId = parameters.get("eventId");

			closure.setProperty("position", cordinatesMonitor);

			actions.add(new Action("mouse.move", eventId, closure) {

				@Override
				public boolean shouldRun() {
					return cordinatesMonitor.hasChanged();
				}
			});
		}
	}

	static interface MonitorFactory{

		ButtonMonitor keyboardButtonMonitor(String button);
		ButtonMonitor mouseButtonMonitor(String button);
		CoordinatesMonitor mouseCoordinatesMonitor();
	}
		
	static class SlickMonitorFactory implements MonitorFactory{

		Input input;

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
		
		@Inject
		public void setInput(Input input) {
			this.input = input;
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

	@Inject
	World world;
	// @Inject Scene scene;
	@Inject
	Input input;
	@Inject
	Game game;
	
	@Inject public void setMonitorFactory(SlickMonitorFactory monitorFactory) {
		this.monitorFactory = monitorFactory;
	}

}
