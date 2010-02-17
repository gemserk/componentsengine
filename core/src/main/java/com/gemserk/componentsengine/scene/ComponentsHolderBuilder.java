package com.gemserk.componentsengine.scene;

import groovy.lang.Closure;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.components.ComponentManager;
import com.gemserk.componentsengine.components.ComponentsHolder;
import com.google.inject.Injector;

public class ComponentsHolderBuilder {

	private ComponentsHolder componentsHolder;

	private PropertiesHolderBuilder propertiesHolderBuilder;

	private ComponentManager componentManager;

	private Injector injector;

	public ComponentsHolderBuilder(ComponentsHolder componentHolder, PropertiesHolderBuilder propertiesHolderBuilder, ComponentManager componentManager, Injector injector) {
		super();
		this.componentsHolder = componentHolder;
		this.propertiesHolderBuilder = propertiesHolderBuilder;
		this.componentManager = componentManager;
		this.injector = injector;
	}

	public void component(String idComponent) {
		componentsHolder.addComponent(componentManager.getComponent(idComponent));
	}

	public void component(Component component) {
		injector.injectMembers(component);
		componentsHolder.addComponent(component);
	}

	public void component(final String idComponent, Closure closure) {
		Component component = componentManager.getComponent(idComponent);
		component(component, closure);
	}

	public void component(final Component component, Closure closure) {
		component(component);

		closure.setDelegate(new Object() {

			@SuppressWarnings("unused")
			public void property(String key, Object value) {
				propertiesHolderBuilder.property(component.getId() + "." + key, value);
			}

			@SuppressWarnings("unused")
			public void propertyRef(String key, String referencedPropertyName) {
				propertiesHolderBuilder.propertyRef(component.getId() + "." + key, referencedPropertyName);
			}

		});
		closure.setResolveStrategy(Closure.DELEGATE_FIRST);
		closure.call();
	}
}