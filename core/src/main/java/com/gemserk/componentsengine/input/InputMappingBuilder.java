package com.gemserk.componentsengine.input;

import com.gemserk.componentsengine.components.Component;

public abstract class InputMappingBuilder {

	MonitorFactory monitorFactory;

	InputMapping inputMapping;

	Component inputMappingComponent;

	private final String id;

	public InputMappingBuilder(String id) {
		this.id = id;
	}

	public Component getInputMappingComponent() {
		return inputMappingComponent;
	}

	public abstract void build();

	public void keyboard(KeyboardMappingBuilder keyboardMappingBuilder) {
		keyboardMappingBuilder.with(inputMapping, monitorFactory).build();
	}

	public void mouse(MouseMappingBuilder mouseMappingBuilder) {
		mouseMappingBuilder.with(inputMapping, monitorFactory).build();
	}

	public InputMappingBuilder with(InputMapping inputMapping, MonitorFactory monitorFactory) {
		this.inputMapping = inputMapping;
		this.monitorFactory = monitorFactory;
		inputMappingComponent = new InputMappingComponent(id, inputMapping);
		return this;
	}

}