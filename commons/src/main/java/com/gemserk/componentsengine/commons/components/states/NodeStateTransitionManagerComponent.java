package com.gemserk.componentsengine.commons.components.states;

import java.util.List;
import java.util.Map;

import com.gemserk.componentsengine.annotations.EntityProperty;
import com.gemserk.componentsengine.commons.components.FieldsReflectionComponent;
import com.gemserk.componentsengine.messages.GenericMessage;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.properties.PropertiesMapBuilder;
import com.google.inject.Inject;

public class NodeStateTransitionManagerComponent extends FieldsReflectionComponent {

	@Inject MessageQueue messageQueue;
	
	@EntityProperty(readOnly=true)
	Map<String, List<String>> transitions;
	
	
	public NodeStateTransitionManagerComponent(String id) {
		super(id);
	}
	
	@Override
	public void handleMessage(Message message) {
		try {
			preHandleMessage(message);
			String messageId = message.getId();
			List<String> transition = transitions.get(messageId);
			if (transition == null)
				return;
			messageQueue.enqueue(new GenericMessage("leaveNodeState", new PropertiesMapBuilder().property("message", message).build()));
			messageQueue.enqueue(new GenericMessage("changeNodeState", new PropertiesMapBuilder().property("states", transition).build()));
			messageQueue.enqueue(new GenericMessage("enterNodeState", new PropertiesMapBuilder().property("message", message).build()));
		} finally {
			postHandleMessage(message);
		}
	}
}
