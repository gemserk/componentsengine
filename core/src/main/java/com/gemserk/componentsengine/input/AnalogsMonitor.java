package com.gemserk.componentsengine.input;

public interface AnalogsMonitor<K> extends MonitorUpdater {

	void analog(K id, AnalogInputMonitor monitor);

	AnalogInputMonitor get(K id);
	
}