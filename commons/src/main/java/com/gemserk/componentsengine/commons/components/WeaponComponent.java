package com.gemserk.componentsengine.commons.components;

import static com.gemserk.componentsengine.properties.Properties.property;

import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.components.ReflectionComponent;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.properties.PropertyLocator;
import com.gemserk.componentsengine.triggers.Trigger;

public class WeaponComponent extends ReflectionComponent {

	PropertyLocator<Boolean> shouldFireProperty;

	PropertyLocator<Vector2f> positionProperty;

	PropertyLocator<Integer> reloadTimeProperty;

	PropertyLocator<Integer> currentReloadTimeProperty;

	PropertyLocator<Trigger> triggerProperty;
	
	public WeaponComponent(String id) {
		super(id);
		shouldFireProperty = property(id, "shouldFire");
		positionProperty = property(id, "position");
		reloadTimeProperty = property(id, "reloadTime");
		currentReloadTimeProperty = property(id, "currentReloadTime");
		triggerProperty = property(id, "trigger");
	}

	@Override
	public void onAdd(Entity entity) {
		super.onAdd(entity);
		currentReloadTimeProperty.setValue(entity, 0);
	}

	@Handles
	public void update(Message message) {
		int delta = (Integer) Properties.getValue(message, "delta");
		Integer currentReloadTime = currentReloadTimeProperty.getValue(entity);

		if (currentReloadTime > 0) {
			currentReloadTime -= delta;
			currentReloadTimeProperty.setValue(entity, currentReloadTime);
			return;
		}

		if (!shouldFireProperty.getValue(entity))
			return;

		Trigger trigger = triggerProperty.getValue(entity);
		trigger.trigger(entity);
		
		currentReloadTime = reloadTimeProperty.getValue(entity);
		currentReloadTimeProperty.setValue(entity, currentReloadTime);
	}

}