package com.gemserk.componentsengine.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InputDevicesMonitorImpl<K> implements ButtonsMonitor<K>, AnalogsMonitor<K> {

	Map<K, ButtonMonitor> digitalMonitorsMap = new HashMap<K, ButtonMonitor>();

	ArrayList<ButtonMonitor> digitalMonitors = new ArrayList<ButtonMonitor>();

	@Override
	public boolean isHolded(K id) {
		if (!digitalMonitorsMap.containsKey(id))
			return false;
		return digitalMonitorsMap.get(id).isHolded();
	}

	public boolean isPressed(K id) {
		if (!digitalMonitorsMap.containsKey(id))
			return false;
		return digitalMonitorsMap.get(id).isPressed();
	}

	public boolean isReleased(K id) {
		if (!digitalMonitorsMap.containsKey(id))
			return false;
		return digitalMonitorsMap.get(id).isReleased();
	}

	@Override
	public void button(K id, ButtonMonitor buttonMonitor) {
		// overrides previous registered monitor with same id
		digitalMonitorsMap.put(id, buttonMonitor);
		digitalMonitors.add(buttonMonitor);
	}

	Map<K, AnalogInputMonitor> analogMonitorsMap = new HashMap<K, AnalogInputMonitor>();

	ArrayList<AnalogInputMonitor> analogMonitors = new ArrayList<AnalogInputMonitor>();

	@Override
	public void update() {
		for (int i = 0; i < digitalMonitors.size(); i++) 
			digitalMonitors.get(i).update();

		for (int i = 0; i < analogMonitors.size(); i++) 
			analogMonitors.get(i).update();
	}

	@Override
	public float getValue(K id) {
		if (!analogMonitorsMap.containsKey(id))
			return 0f;
		return analogMonitorsMap.get(id).getValue();
	}
	
	@Override
	public float getOldValue(K id) {
		if (!analogMonitorsMap.containsKey(id))
			return 0f;
		return analogMonitorsMap.get(id).getOldValue();
	}
	
	@Override
	public boolean hasChanged(K id) {
		if (!analogMonitorsMap.containsKey(id))
			return false;
		return analogMonitorsMap.get(id).hasChanged();
	}

	@Override
	public void analog(K id, AnalogInputMonitor monitor) {
		// overrides previous registered monitor with same id
		analogMonitorsMap.put(id, monitor);
		analogMonitors.add(monitor);
	}

}