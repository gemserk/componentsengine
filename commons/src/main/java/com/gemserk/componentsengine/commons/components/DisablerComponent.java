package com.gemserk.componentsengine.commons.components;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.properties.PropertyLocator;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class DisablerComponent extends Component {

	private PropertyLocator<Boolean> enabledProperty;
	
	private final Component component;
	
	@Inject Injector injector;

	public DisablerComponent(Component component) {
		super(component.getId());
		this.component = component;
		enabledProperty = Properties.property(id, "enabled");
	}
	
	@Override
	public void onAdd(Entity entity) {
		super.onAdd(entity);
		injector.injectMembers(component);
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