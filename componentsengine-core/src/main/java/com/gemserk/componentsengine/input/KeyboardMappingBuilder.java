package com.gemserk.componentsengine.input;

import com.gemserk.componentsengine.input.ButtonMonitor.Status;

/**
 * Builder class to be used with InputMappingBuilder.keyboard(...)
 */
public abstract class KeyboardMappingBuilder {

	InputMapping inputMapping;

	MonitorFactory monitorFactory;

	public KeyboardMappingBuilder with(InputMapping inputMapping, MonitorFactory monitorFactory) {
		this.inputMapping = inputMapping;
		this.monitorFactory = monitorFactory;
		return this;
	}

	public void press(String button, String eventId) {
		addAction(button, eventId, ButtonMonitor.Status.PRESSED);
	}

	public void hold(String button, String eventId) {
		addAction(button, eventId, ButtonMonitor.Status.HOLDED);
	}

	public void release(String button, String eventId) {
		addAction(button, eventId, ButtonMonitor.Status.RELEASED);
	}

	public void addAction(String button, String eventId, final Status status) {
		final ButtonMonitor buttonMonitor = monitorFactory.keyboardButtonMonitor(button);

		inputMapping.addAction(new AbstractInputAction(eventId) {
			@Override
			public boolean shouldRun() {
				return buttonMonitor.status(status);
			}
		});
	}

	public abstract void build();
}