package com.gemserk.componentsengine.commons.components;

import java.util.HashMap;

import com.gemserk.componentsengine.components.ReflectionComponent;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.properties.PropertyLocator;
import com.gemserk.componentsengine.timers.Timer;
import com.gemserk.componentsengine.triggers.Trigger;

public class TimerComponent extends ReflectionComponent {

	private PropertyLocator<Timer> timerProperty;
	
	private PropertyLocator<Trigger> triggerProperty;

	public TimerComponent(String id) {
		super(id);
		timerProperty = Properties.property(id, "timer");
		triggerProperty = Properties.property(id, "trigger");
	}

	@Handles
	public void update(Message message) {
		int delta = (Integer) Properties.getValue(message, "delta");
		Timer timer = timerProperty.getValue(entity);
		boolean fired = timer.update(delta);
		if (!fired)
			return;

		triggerProperty.getValue(entity).trigger(new HashMap<String, Object>());

	}

}
