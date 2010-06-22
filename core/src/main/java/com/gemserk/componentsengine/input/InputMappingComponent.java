package com.gemserk.componentsengine.input;

import java.util.List;

import com.gemserk.componentsengine.components.ReflectionComponent;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.google.inject.Inject;

public class InputMappingComponent extends ReflectionComponent {

	InputMapping inputMapping;
	
	@Inject MessageQueue messageQueue;
	
	public InputMappingComponent(String id, InputMapping inputMapping) {
		super(id);
		this.inputMapping = inputMapping;
	}

	@Handles
	public void update(Message message){
		List<Message> messages = inputMapping.update();
		for (Message newMessage : messages) {
			messageQueue.enqueue(newMessage);
		}
	}
	
}
