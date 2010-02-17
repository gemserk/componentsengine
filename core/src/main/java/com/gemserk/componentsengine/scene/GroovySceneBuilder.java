package com.gemserk.componentsengine.scene;

import groovy.lang.Closure;
import groovy.lang.MissingPropertyException;

import java.util.HashMap;
import java.util.Map;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.components.ComponentManager;
import com.gemserk.componentsengine.controllers.InputController;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.input.GroovyInputMappingBuilder;
import com.gemserk.componentsengine.messages.GenericMessage;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.resources.PropertiesImageLoader;
import com.gemserk.componentsengine.resources.ResourceLoader;
import com.gemserk.componentsengine.templates.TemplateProvider;
import com.gemserk.componentsengine.world.World;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class GroovySceneBuilder {

	TemplateProvider templateProvider;

	Injector injector;

	Scene scene;

	ComponentManager componentManager;

	GroovyInputMappingBuilder inputMappingBuilder;

	BuilderUtils utils;

	private PropertiesHolderBuilder propertiesHolderBuilder;

	private ComponentsHolderBuilder componentHolderBuilder;

	@Inject
	public void setComponentManager(ComponentManager componentManager) {
		this.componentManager = componentManager;
	}

	@Inject
	public void setTemplateProvider(TemplateProvider templateProvider) {
		this.templateProvider = templateProvider;
	}

	@Inject
	public void setInjector(Injector injector) {
		this.injector = injector;
	}

	@Inject
	public void setInputMappingBuilder(GroovyInputMappingBuilder inputMappingBuilder) {
		this.inputMappingBuilder = inputMappingBuilder;
	}

	@Inject
	public void setUtils(BuilderUtils utils) {
		this.utils = utils;
	}

	public Scene scene(String id) {
		if (scene == null)
			scene = new Scene(id);

		propertiesHolderBuilder = new PropertiesHolderBuilder(scene);
		componentHolderBuilder = new ComponentsHolderBuilder(scene, propertiesHolderBuilder, componentManager, injector);

		injector.injectMembers(scene);

		return scene;
	}

	public Scene scene(String id, Closure closure) {
		scene = scene(id);

		closure.setDelegate(this);
		closure.call();
		return scene;
	}

	public void entity(String templateName, String entityName, Map<String, Object> parameters) {
		Entity entity = templateProvider.getTemplate(templateName).instantiate(entityName, parameters);
		scene.getWorld().addEntity(entity);
	}

	public void entity(Map<String, Object> dslParameters) {
		String entityName = (String) dslParameters.get("id");
		String templateName = (String) dslParameters.get("template");
		Map<String, Object> parameters = (Map<String, Object>) dslParameters.get("parameters");
		entity(templateName, entityName, parameters);
	}

	public void entity(Map<String, Object> dslParameters, Closure closure) {
		String entityName = (String) dslParameters.get("id");
		String templateName = (String) dslParameters.get("template");

		MapBuilder mapBuilder = new MapBuilder();

		closure.setResolveStrategy(Closure.DELEGATE_FIRST);
		closure.setDelegate(mapBuilder);

		closure.call();

		entity(templateName, entityName, mapBuilder.getParameters());
	}

	class MapBuilder {
		Map<String, Object> parameters = new HashMap<String, Object>();

		public Object propertyMissing(String name) {
			Object value = parameters.get(name);
			if (value != null)
				return value;
			else
				throw new MissingPropertyException("failed to get property " + name);

		}

		public void propertyMissing(String name, Object value) {
			parameters.put(name, value);
		}

		public Map<String, Object> getParameters() {
			return parameters;
		}

		public void properties(Map forcedProperties) {
			parameters.put("properties", forcedProperties);
		}
	}

	public void components(Class<? extends ResourceLoader> loaderClass) {
		try {
			ResourceLoader resourceLoader = loaderClass.newInstance();
			injector.injectMembers(resourceLoader);
			resourceLoader.load();
		} catch (Exception e) {
			throw new RuntimeException("cant load componentloader", e);
		}
	}

	void genericComponent(final Map<String, Object> parameters, final Closure closure) {
		componentHolderBuilder.component(new Component((String) parameters.get("id")) {

			@Inject
			World world;

			@Inject
			MessageQueue messageQueue;

			Scene scene = GroovySceneBuilder.this.scene;

			@Override
			public void handleMessage(Message message) {
				if (message instanceof GenericMessage) {
					GenericMessage genericMessage = (GenericMessage) message;
					if (!parameters.get("messageId").equals(genericMessage.getId()))
						return;

					closure.setDelegate(this);
					closure.call(genericMessage);
				}
			}

		});
	}

	void property(String key, Object value) {
		propertiesHolderBuilder.property(key, value);
	}

	void propertyRef(String key, String referencedPropertyName) {
		propertiesHolderBuilder.propertyRef(key, referencedPropertyName);
	}

	public void controller(InputController inputController) {
		injector.injectMembers(inputController);
		inputController.register();
	}

	public void controller(Class<? extends InputController> loaderClass) {
		try {
			InputController inputcontroller = loaderClass.newInstance();
			injector.injectMembers(inputcontroller);
			inputcontroller.register();
		} catch (Exception e) {
			throw new RuntimeException("cant load inputcontroller", e);
		}
	}

	public void images(String imagePropertiesFile) {
		PropertiesImageLoader propertiesImageLoader = new PropertiesImageLoader(imagePropertiesFile);
		injector.injectMembers(propertiesImageLoader);
		propertiesImageLoader.load();

	}

	public void input(String id, Closure closure) {
		Component inputcomponent = inputMappingBuilder.configure(id, closure);
		component(inputcomponent);
	}

	public void input(String id, String mapping) {
		Component inputcomponent = inputMappingBuilder.configure(id, mapping);
		component(inputcomponent);
	}

	public void component(Component component, Closure closure) {
		componentHolderBuilder.component(component, closure);
	}

	public void component(Component component) {
		componentHolderBuilder.component(component);
	}

	public void component(String idComponent, Closure closure) {
		componentHolderBuilder.component(idComponent, closure);
	}

	public void component(String idComponent) {
		componentHolderBuilder.component(idComponent);
	}

}