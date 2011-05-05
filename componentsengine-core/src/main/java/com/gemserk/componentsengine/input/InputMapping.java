package com.gemserk.componentsengine.input;

import java.util.ArrayList;
import java.util.List;

import com.gemserk.componentsengine.messages.Message;

public class InputMapping {

	private List<InputAction> inputActions = new ArrayList<InputAction>();

	public void addAction(InputAction inputAction) {
		inputActions.add(inputAction);
	}

	public List<Message> update() {
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