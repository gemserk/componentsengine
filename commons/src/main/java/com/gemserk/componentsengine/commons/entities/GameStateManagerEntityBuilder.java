package com.gemserk.componentsengine.commons.entities;

import java.util.Map;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.components.FieldsReflectionComponent;
import com.gemserk.componentsengine.components.annotations.EntityProperty;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.messages.ChildrenManagementMessageFactory;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.messages.messageBuilder.MessageBuilder;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.properties.PropertiesMapBuilder;
import com.gemserk.componentsengine.templates.EntityBuilder;
import com.google.inject.Inject;

public class GameStateManagerEntityBuilder extends EntityBuilder {

	
	
	static class HandleStateChangedComponent extends FieldsReflectionComponent {
		
		@Inject
		MessageQueue messageQueue;
		
		@EntityProperty
		Entity currentState;
		
		@Inject ChildrenManagementMessageFactory childrenManagementMessageFactory;

		HandleStateChangedComponent(String id) {
			super(id);
		}

		@Handles
		public void changeNodeState(Message message) {

			Entity newState = Properties.getValue(message, "state");

			if (newState == currentState)
				return;

			if (currentState != null)
				messageQueue.enqueue(childrenManagementMessageFactory.removeEntity(currentState));

			messageQueue.enqueue(childrenManagementMessageFactory.addEntity(newState, entity));

			currentState = newState;
		}
	}

	static class HandleStateTransitionComponent extends Component {

		@Inject
		MessageQueue messageQueue;
		
		@Inject MessageBuilder messageBuilder;

		HandleStateTransitionComponent(String id) {
			super(id);
		}

		@Override
		public void handleMessage(Message message) {

			Map<String, String> transitions = Properties.getValue(entity, getId() + ".transitions");

			if (!transitions.containsKey(message.getId()))
				return;

			String transition = transitions.get(message.getId());

			Map<String, Entity> stateEntities = Properties.getValue(entity, getId() + ".stateEntities");

			Entity stateEntity = stateEntities.get(transition);

			messageQueue.enqueueDelay(messageBuilder.newMessage("leaveNodeState").property("message",messageBuilder.clone(message)).get());
			messageQueue.enqueueDelay(messageBuilder.newMessage("changeNodeState").property("state", stateEntity).get());
			messageQueue.enqueueDelay(messageBuilder.newMessage("enterNodeState").property("message",messageBuilder.clone(message)).get());
		}
	}

	@Override
	public void build() {

		tags("gameStateManager");

		property("transitions", parameters.get("transitions"));

		property("stateEntities", parameters.get("stateEntities"));

		property("currentState", null);

		component(new HandleStateTransitionComponent("handleTransitionComponent")).withProperties(new ComponentProperties() {
			{
				propertyRef("transitions", "transitions");
				propertyRef("stateEntities", "stateEntities");
			}
		});

		component(new HandleStateChangedComponent("handleChangeStateComponent")).withProperties(new ComponentProperties() {
			{
				propertyRef("currentState", "currentState");
			}
		});

	}

}
