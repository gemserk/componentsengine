package com.gemserk.componentsengine.input;

public interface AnalogsMonitor<K> extends MonitorUpdater {

	float getValue(K id);

	float getOldValue(K id);

	boolean hasChanged(K id);

	void analog(K id, AnalogInputMonitor monitor);

}