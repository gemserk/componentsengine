package com.gemserk.componentsengine.input;

import com.gemserk.componentsengine.components.Component;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class InputMappingBuilderConfigurator {

	MonitorFactory monitorFactory;

	Provider<InputMapping> inputMappingProvider;

	@Inject
	public void setInputMappingProvider(Provider<InputMapping> inputMappingProvider) {
		this.inputMappingProvider = inputMappingProvider;
	}

	@Inject
	public void setMonitorFactory(MonitorFactory monitorFactory) {
		this.monitorFactory = monitorFactory;
	}
	
	public Component configure(InputMappingBuilder inputMappingBuilder) {
		inputMappingBuilder.with(inputMappingProvider.get(), monitorFactory).build();
		return inputMappingBuilder.getInputMappingComponent();
	}

}