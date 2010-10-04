package com.gemserk.componentsengine.commons.components.states;

import java.util.List;
import java.util.Map;

import com.gemserk.componentsengine.commons.components.FieldsReflectionComponent;
import com.gemserk.componentsengine.components.annotations.EntityProperty;
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
			messageQueue.enqueueDelay(new Message("leaveNodeState", new PropertiesMapBuilder().property("message", message).build()));
			messageQueue.enqueueDelay(new Message("changeNodeState", new PropertiesMapBuilder().property("states", transition).build()));
			messageQueue.enqueueDelay(new Message("enterNodeState", new PropertiesMapBuilder().property("message", message).build()));
		} finally {
			postHandleMessage(message);
		}
	}
}
