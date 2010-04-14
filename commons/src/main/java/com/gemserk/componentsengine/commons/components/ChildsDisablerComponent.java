package com.gemserk.componentsengine.commons.components;

import java.util.ArrayList;
import java.util.Collection;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.properties.PropertyLocator;

@SuppressWarnings("unchecked")
public class ChildsDisablerComponent extends Component {

	PropertyLocator<Boolean> enabledProperty;
	
	PropertyLocator<Collection<Class>> exclusionMessageListProperty;

	public ChildsDisablerComponent(String id) {
		super(id);
		enabledProperty = Properties.property(id, "enabled");
		exclusionMessageListProperty = Properties.property(id, "exclusions");
	}

	@Override
	public void handleMessage(Message message) {
		
		boolean enabled = enabledProperty.getValue(entity);
		
		if (!enabled) {
			Collection<Class> exclusions = exclusionMessageListProperty.getValue(entity, new ArrayList<Class>());
			if (exclusions.contains(message.getClass()))
				return;
			message.suspendPropagation();
		}
		
	}
}