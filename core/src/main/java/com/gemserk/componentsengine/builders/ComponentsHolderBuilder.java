package com.gemserk.componentsengine.builders;

import groovy.lang.Closure;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.components.ComponentsHolder;
import com.google.inject.Injector;

public class ComponentsHolderBuilder {

	private ComponentsHolder componentsHolder;

	private PropertiesHolderBuilder propertiesHolderBuilder;


	private Injector injector;

	public ComponentsHolderBuilder(ComponentsHolder componentHolder, PropertiesHolderBuilder propertiesHolderBuilder, Injector injector) {
		super();
		this.componentsHolder = componentHolder;
		this.propertiesHolderBuilder = propertiesHolderBuilder;
		this.injector = injector;
	}


	public void component(Component component) {
		injector.injectMembers(component);
		componentsHolder.addComponent(component);
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
			
			@SuppressWarnings("unused")
			public void property(String key, Closure closure) {
				propertiesHolderBuilder.property(component.getId() + "." + key, closure);
			}


		});
		closure.setResolveStrategy(Closure.DELEGATE_FIRST);
		closure.call();
	}
}