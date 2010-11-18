package com.gemserk.componentsengine.input;

import com.gemserk.componentsengine.input.ButtonMonitor.Status;

/**
 * Builder class to be used with InputMappingBuilder.mouse(...)
 */
public abstract class MouseMappingBuilder {

	InputMapping inputMapping;

	MonitorFactory monitorFactory;

	public MouseMappingBuilder with(InputMapping inputMapping, MonitorFactory monitorFactory) {
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
	
	void addAction(String button, String eventId, final Status status) {
		final ButtonMonitor buttonMonitor = monitorFactory.mouseButtonMonitor(button);

		inputMapping.addAction(new AbstractInputAction(eventId) {
			@Override
			public boolean shouldRun() {
				return buttonMonitor.status(status);
			}
		});
	}
	
	public void move(String eventId) {
		final CoordinatesMonitor cordinatesMonitor = monitorFactory.mouseCoordinatesMonitor();
		inputMapping.addAction(new AbstractInputAction(eventId) {
			
			@Override
			public boolean shouldRun() {
				return cordinatesMonitor.hasChanged();
			}
		});
		
	}

	public abstract void build();
	
}