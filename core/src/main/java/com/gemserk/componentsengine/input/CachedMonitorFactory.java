package com.gemserk.componentsengine.input;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class CachedMonitorFactory implements MonitorFactory, MonitorUpdater {

	MonitorFactory monitorFactory;
	
	Map<String,ButtonMonitor> keyboardButtonMonitors = new HashMap<String, ButtonMonitor>();
	Map<String,ButtonMonitor> mouseButtonMonitors = new HashMap<String, ButtonMonitor>();
	CoordinatesMonitor coordinatesMonitor = null;
	
	
	public CachedMonitorFactory(MonitorFactory monitorFactory) {
		this.monitorFactory = monitorFactory;
	}

	@Override
	public ButtonMonitor keyboardButtonMonitor(String button) {
		if(!keyboardButtonMonitors.containsKey(button))
			keyboardButtonMonitors.put(button,monitorFactory.keyboardButtonMonitor(button));
		
		return keyboardButtonMonitors.get(button);
	}

	@Override
	public ButtonMonitor mouseButtonMonitor(String button) {
		if(!mouseButtonMonitors.containsKey(button))
			mouseButtonMonitors.put(button,monitorFactory.mouseButtonMonitor(button));
		
		return mouseButtonMonitors.get(button);
	}

	@Override
	public CoordinatesMonitor mouseCoordinatesMonitor() {
		if(coordinatesMonitor==null)
			coordinatesMonitor = monitorFactory.mouseCoordinatesMonitor();
		
		return coordinatesMonitor;
	}

	@Override
	public void update() {
		for (Entry<String, ButtonMonitor> entry : keyboardButtonMonitors.entrySet()) {
			entry.getValue().update();
		}
		
		for (Entry<String, ButtonMonitor> entry : mouseButtonMonitors.entrySet()) {
			entry.getValue().update();
		}
		
		if(coordinatesMonitor != null)
			coordinatesMonitor.update();
	}

}
