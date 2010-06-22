package com.gemserk.componentsengine.input;

import com.gemserk.componentsengine.components.ReflectionComponent;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.messages.Message;
import com.google.inject.Inject;

public class InputMonitorUpdaterComponent extends ReflectionComponent{

	@Inject MonitorUpdater monitorUpdater;
	
	public InputMonitorUpdaterComponent(String id) {
		super(id);
	}
	
	@Handles
	public void update(Message message){
		monitorUpdater.update();
	}
	

}
