package com.gemserk.componentsengine.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.gemserk.componentsengine.components.MessageHandler;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.UpdateMessage;
import com.google.inject.Inject;

public class InputMapping implements MessageHandler {

	MessageHandler handler;

	List<InputAction> inputActions = new ArrayList<InputAction>();

	Map<InputKey, ButtonMonitor> buttonMonitors = new HashMap<InputKey, ButtonMonitor>();
	
	Map<InputKey, CoordinatesMonitor> coordinatesMonitors = new HashMap<InputKey, CoordinatesMonitor>();

	public void addButtonMonitor(InputKey key, ButtonMonitor monitor) {
		buttonMonitors.put(key, monitor);
	}

	public ButtonMonitor getButtonMonitor(InputKey key) {
		return buttonMonitors.get(key);
	}

	public CoordinatesMonitor getCoordinatesMonitor(InputKey key) {
		return coordinatesMonitors.get(key);
	}

	public void addCoordinatesMonitor(InputKey key, CoordinatesMonitor monitor) {
		coordinatesMonitors.put(key, monitor);
	}

	public void addAction(InputAction inputAction) {
		inputActions.add(inputAction);
	}

	@Inject
	public void setHandler(MessageHandler handler) {
		this.handler = handler;
	}

	@Override
	public void handleMessage(Message message) {
		if (message instanceof UpdateMessage) {
			
			for (Entry<InputKey, ButtonMonitor> entry : buttonMonitors.entrySet()) 
				entry.getValue().update();
			
			for (Entry<InputKey, CoordinatesMonitor> entry : coordinatesMonitors.entrySet()) 
				entry.getValue().update();

			for (InputAction inputAction : inputActions) {
				Message newMessage = inputAction.run();
				if (newMessage != null) {
					handler.handleMessage(newMessage);
				}
			}
		}

	}

}