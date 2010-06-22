package com.gemserk.componentsengine.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.gemserk.componentsengine.messages.Message;

public class InputMapping {
	
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

	public List<Message> update() {
			
			for (Entry<InputKey, ButtonMonitor> entry : buttonMonitors.entrySet()) 
				entry.getValue().update();
			
			for (Entry<InputKey, CoordinatesMonitor> entry : coordinatesMonitors.entrySet()) 
				entry.getValue().update();

			List<Message> messages = new ArrayList<Message>(inputActions.size());
			for (InputAction inputAction : inputActions) {
				Message newMessage = inputAction.run();
				if (newMessage != null) {
					messages.add(newMessage);
				}
			}
			return messages;

	}

}