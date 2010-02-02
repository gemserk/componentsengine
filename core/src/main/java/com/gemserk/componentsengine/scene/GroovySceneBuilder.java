/**
 * 
 */
package com.gemserk.componentsengine.scene;

import groovy.lang.Closure;
import groovy.lang.MissingPropertyException;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.components.ComponentManager;
import com.gemserk.componentsengine.controllers.InputController;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.resources.PropertiesImageLoader;
import com.gemserk.componentsengine.resources.ResourceLoader;
import com.gemserk.componentsengine.templates.TemplateProvider;
import com.gemserk.componentsengine.utils.Interval;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class GroovySceneBuilder {

	TemplateProvider templateProvider;

	Injector injector;

	Scene scene;

	ComponentManager componentManager;

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

	public Scene scene(String id) {
		if (scene == null)
			scene = new Scene(id);

		injector.injectMembers(scene);

		return scene;
	}

	public Scene scene(String id, Closure closure) {
		if (scene == null)
			scene = new Scene(id);

		injector.injectMembers(scene);

		closure.setDelegate(this);
		closure.call();
		return scene;
	}

	public void entity(String templateName, String entityName,
			Map<String, Object> parameters) {
		Entity entity = templateProvider.getTemplate(templateName).instantiate(
				entityName, parameters);
		scene.getWorld().addEntity(entity);
	}

	public void entity(Map<String, Object> dslParameters) {
		String entityName = (String) dslParameters.get("id");
		String templateName = (String) dslParameters.get("template");
		Map<String, Object> parameters = (Map<String, Object>) dslParameters
				.get("parameters");
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
				throw new MissingPropertyException("no encontre la prop");
		}

		public void propertyMissing(String name, Object value) {
			parameters.put(name, value);
		}

		public Map<String, Object> getParameters() {
			return parameters;
		}

	}

	public void component(Component component) {
		injector.injectMembers(component);
		scene.addComponent(component);
	}

	void components(Class<? extends ResourceLoader> loaderClass) {
		try {
			ResourceLoader resourceLoader = loaderClass.newInstance();
			injector.injectMembers(resourceLoader);
			resourceLoader.load();
		} catch (Exception e) {
			throw new RuntimeException("cant load componentloader", e);
		}
	}

	void component(String idComponent) {
		scene.addComponent(componentManager.getComponent(idComponent));
	}

	public void controller(InputController inputController) {
		injector.injectMembers(inputController);
		inputController.register();
	}

	void controller(Class<? extends InputController> loaderClass) {
		try {
			InputController inputcontroller = loaderClass.newInstance();
			injector.injectMembers(inputcontroller);
			inputcontroller.register();
		} catch (Exception e) {
			throw new RuntimeException("cant load inputcontroller", e);
		}
	}

	public void images(String imagePropertiesFile) {
		PropertiesImageLoader propertiesImageLoader = new PropertiesImageLoader(
				imagePropertiesFile);
		injector.injectMembers(propertiesImageLoader);
		propertiesImageLoader.load();

	}

	Utils utils = new Utils();

	public Utils getUtils() {
		return utils;
	}

	class Utils {
		public Vector2f vector(float x, float y) {
			return new Vector2f(x, y);
		}

		public Interval interval(int min, int max) {
			return new Interval(min, max);
		}

		public Rectangle rectangle(float x, float y, float width, float height) {
			return new Rectangle(x, y, width, height);
		}

		public Color color(float r, float g, float b, float a) {
			return new Color(r, g, b, a);
		}

		public Color color(float r, float g, float b) {
			return new Color(r, g, b);
		}

	}
}