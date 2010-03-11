package com.gemserk.componentsengine.commons.components;

import java.util.HashMap;

import com.gemserk.componentsengine.components.ReflectionComponent;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.messages.UpdateMessage;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.properties.PropertyLocator;
import com.gemserk.componentsengine.timers.Timer;
import com.gemserk.componentsengine.triggers.Trigger;
import com.google.inject.Inject;

public class TimerComponent extends ReflectionComponent {

	private PropertyLocator<Timer> timerProperty;
	private PropertyLocator<Trigger> triggerProperty;
	
	@Inject 
	MessageQueue messageQueue;

	public TimerComponent(String id) {
		super(id);
		timerProperty = Properties.property(id, "timer");
		triggerProperty = Properties.property(id, "trigger");
	}

	public void handleMessage(UpdateMessage message) {
		Timer timer = timerProperty.getValue(entity);
		boolean fired = timer.update(message.getDelta());
		if (!fired)
			return;

		triggerProperty.getValue(entity).trigger(new HashMap<String, Object>());

	}

}
