package com.gemserk.componentsengine.commons.components;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.properties.PropertyLocator;

public class DisablerComponent extends Component {

	private PropertyLocator<Boolean> enabledProperty;
	
	private final Component component;

	public DisablerComponent(Component component) {
		super(component.getId());
		this.component = component;
		enabledProperty = Properties.property(id, "enabled");
	}
	
	@Override
	public void onAdd(Entity entity) {
		super.onAdd(entity);
		component.onAdd(entity);
	}

	@Override
	public void handleMessage(Message message) {
		boolean enabled = enabledProperty.getValue(entity, true);
		if (!enabled)
			return;
		component.handleMessage(message);
	}

}