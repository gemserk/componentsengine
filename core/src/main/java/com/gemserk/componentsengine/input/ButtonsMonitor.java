package com.gemserk.componentsengine.input;

public interface ButtonsMonitor<K> extends MonitorUpdater {

	boolean isHolded(K id);

	boolean isPressed(K id);

	boolean isReleased(K id);

	void button(K id, ButtonMonitor buttonMonitor);

}
