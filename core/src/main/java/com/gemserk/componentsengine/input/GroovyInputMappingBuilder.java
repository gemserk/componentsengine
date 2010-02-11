package com.gemserk.componentsengine.input;

import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.GroovyClassLoader;
import groovy.lang.Script;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Mouse;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.input.ButtonMonitor.Status;
import com.gemserk.componentsengine.templates.GroovyScriptProvider;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class GroovyInputMappingBuilder {

	Provider<InputMapping> inputMappingProvider;

	@Inject public void setInputMappingProvider(Provider<InputMapping> inputMappingProvider) {
		this.inputMappingProvider = inputMappingProvider;
	}

	InputMapping inputMapping = null;

	MonitorFactory monitorFactory;

	@Inject	public void setMonitorFactory(MonitorFactory monitorFactory) {
		this.monitorFactory = monitorFactory;
	}
	
	GroovyScriptProvider scriptProvider;
	
	@Inject public void setScriptProvider(GroovyScriptProvider scriptProvider) {
		this.scriptProvider = scriptProvider;
	}

	public Component configure(String id, Closure closure) {
		inputMapping = inputMappingProvider.get();
		closure.setDelegate(new RootNode());
		closure.call();

		return new MessageHandlerToComponentConverter(id, inputMapping);
	}

	class RootNode {
		public void keyboard(Closure closure) {
			closure.setDelegate(new KeyboardNode());
			closure.call();
		}

		public void mouse(Closure closure) {
			closure.setDelegate(new MouseNode());
			closure.call();
		}
	}

	class KeyboardNode {

		public ButtonMonitor getButtonMonitor(String button) {
			InputKey inputKey = new InputKey("keyboard.", button);
			ButtonMonitor buttonMonitor = inputMapping.getButtonMonitor(inputKey);
			if (buttonMonitor == null) {
				buttonMonitor = monitorFactory.keyboardButtonMonitor(button);
				inputMapping.addButtonMonitor(inputKey, buttonMonitor);
			}

			return buttonMonitor;
		}

		public void press(Map<String, String> parameters) {
			press(parameters, null);
		}

		public void press(Map<String, String> parameters, Closure closure) {

			addAction(parameters, closure, ButtonMonitor.Status.PRESSED);
		}

		private void addAction(Map<String, String> parameters, Closure closure, final Status status) {
			final String button = parameters.get("button");
			String eventId = parameters.get("eventId");

			final ButtonMonitor buttonMonitor = getButtonMonitor(button);

			inputMapping.addAction(new GroovyInputAction(button, eventId, closure) {

				@Override
				public boolean shouldRun() {
					return buttonMonitor.status(status);
				}

			});
		}

		public void hold(Map<String, String> parameters) {
			hold(parameters, null);
		}

		public void hold(Map<String, String> parameters, Closure closure) {
			addAction(parameters, closure, ButtonMonitor.Status.HOLDED);
		}

		public void release(Map<String, String> parameters) {
			release(parameters, null);
		}

		public void release(Map<String, String> parameters, Closure closure) {
			addAction(parameters, closure, ButtonMonitor.Status.RELEASED);
		}
	}

	class MouseNode {

		public ButtonMonitor getButtonMonitor(String button) {
			InputKey inputKey = new InputKey("mouse.", button);
			ButtonMonitor buttonMonitor = inputMapping.getButtonMonitor(inputKey);
			if (buttonMonitor == null) {
				buttonMonitor = monitorFactory.mouseButtonMonitor(button);
				inputMapping.addButtonMonitor(inputKey, buttonMonitor);
			}

			return buttonMonitor;
		}

		public CoordinatesMonitor getCoordinatesMonitor() {
			InputKey inputKey = new InputKey("mouse.move", "");
			CoordinatesMonitor coordinatesMonitor = inputMapping.getCoordinatesMonitor(inputKey);
			if (coordinatesMonitor == null) {
				coordinatesMonitor = monitorFactory.mouseCoordinatesMonitor();
				inputMapping.addCoordinatesMonitor(inputKey, coordinatesMonitor);
			}

			return coordinatesMonitor;
		}

		Map<String, String> mappingMouseKeys = new HashMap<String, String>() {
			{
				put("left", "BUTTON0");
				put("right", "BUTTON1");
				put("middle", "BUTTON2");
			}
		};

		public int mapMouseKey(String key) {
			String mappedKey = mappingMouseKeys.get(key);

			if (mappedKey == null)
				mappedKey = key;

			return Mouse.getButtonIndex(mappedKey);
		}

		public void hold(Map<String, String> parameters) {
			hold(parameters, null);
		}

		public void hold(Map<String, String> parameters, Closure closure) {

			addAction(parameters, closure, ButtonMonitor.Status.HOLDED);
		}

		public void press(Map<String, String> parameters) {
			press(parameters, null);
		}

		public void press(Map<String, String> parameters, Closure closure) {
			addAction(parameters, closure, ButtonMonitor.Status.PRESSED);
		}

		public void release(Map<String, String> parameters) {
			release(parameters, null);
		}

		public void release(Map<String, String> parameters, Closure closure) {
			addAction(parameters, closure, ButtonMonitor.Status.RELEASED);
		}

		private void addAction(Map<String, String> parameters, Closure closure, final Status status) {
			final String button = parameters.get("button");
			String eventId = parameters.get("eventId");

			final ButtonMonitor buttonMonitor = getButtonMonitor(button);

			inputMapping.addAction(new GroovyInputAction(button, eventId, closure) {

				@Override
				public boolean shouldRun() {
					return buttonMonitor.status(status);
				}

			});
		}

		public void move(Map<String, String> parameters) {
			move(parameters, null);
		}

		public void move(Map<String, String> parameters, Closure closure) {

			final CoordinatesMonitor cordinatesMonitor = getCoordinatesMonitor();

			String eventId = parameters.get("eventId");

			closure.setProperty("position", cordinatesMonitor);

			inputMapping.addAction(new GroovyInputAction("mouse.move", eventId, closure) {

				@Override
				public boolean shouldRun() {
					return cordinatesMonitor.hasChanged();
				}
			});
		}
	}

	public Component configure(String id, String mapping) {
		try {
			Class<Script> scriptClass = scriptProvider.load(mapping);
			Script script = scriptClass.newInstance();
			
			inputMapping = inputMappingProvider.get();
			Binding binding = new Binding();
			binding.setVariable("builder", new Object() {
				public void input(Closure closure) {
					closure.setDelegate(new RootNode());
					closure.call();
				}
			});

			script.setBinding(binding);
			script.run();
			return new MessageHandlerToComponentConverter(id, inputMapping);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}