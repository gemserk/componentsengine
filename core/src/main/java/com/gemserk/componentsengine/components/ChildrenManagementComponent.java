package com.gemserk.componentsengine.components;

import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.messages.AddEntityMessage;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.RemoveEntityMessage;

public class ChildrenManagementComponent extends Component {

	public ChildrenManagementComponent(String id) {
		super(id);
	}

	@Override
	public void handleMessage(Message message) {

		if (message instanceof RemoveEntityMessage)
			handleRemoveEntityMessage((RemoveEntityMessage) message);
		else if (message instanceof AddEntityMessage)
			handleAddEntityMessage((AddEntityMessage) message);

		super.handleMessage(message);
	}

	private void handleAddEntityMessage(AddEntityMessage addEntityMessage) {
		Entity childEntity = entity.getEntityById(addEntityMessage.getWhereEntityId());
		if (childEntity != null)
			childEntity.addEntity(addEntityMessage.getEntity());
	}

	private void handleRemoveEntityMessage(RemoveEntityMessage removeEntityMessage) {
		Entity childEntity = entity.getEntityById(removeEntityMessage.getEntityName());
		if (childEntity != null)
			childEntity.removeFromParent();
	}
}
