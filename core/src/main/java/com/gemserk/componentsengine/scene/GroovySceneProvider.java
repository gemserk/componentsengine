/**
 * 
 */
package com.gemserk.componentsengine.scene;

import groovy.lang.Binding;
import groovy.lang.Script;

import java.util.HashMap;
import java.util.Map;

import com.gemserk.componentsengine.components.ComponentManager;
import com.gemserk.componentsengine.resources.AnimationManager;
import com.gemserk.componentsengine.resources.AnimationManagerImpl;
import com.gemserk.componentsengine.resources.ImageManager;
import com.gemserk.componentsengine.resources.ImageManagerImpl;
import com.gemserk.componentsengine.templates.GroovyScriptProvider;
import com.gemserk.componentsengine.templates.GroovyTemplateProvider;
import com.gemserk.componentsengine.templates.TemplateNotFoundException;
import com.gemserk.componentsengine.templates.TemplateProvider;
import com.gemserk.componentsengine.world.World;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

public class GroovySceneProvider implements SceneProvider {

	GroovyScriptProvider groovyScriptProvider;

	Injector injector;

	@Inject
	public void setInjector(Injector injector) {
		this.injector = injector;
	}

	@Inject
	public void setGroovyScriptProvider(GroovyScriptProvider groovyScriptProvider) {
		this.groovyScriptProvider = groovyScriptProvider;
	}

	public GroovySceneProvider() {

	}

	private GroovySceneTemplate innerGetTemplate(String name, GroovySceneBuilder groovySceneBuilder) {
		Class<Script> scriptClass;
		try {
			scriptClass = groovyScriptProvider.load(name);
			return new GroovySceneTemplate(scriptClass, groovySceneBuilder);
		} catch (Exception e) {
			throw new TemplateNotFoundException("Unable to load scene template", e);
		}
	}

	public Scene getScene(String sceneName) {
		return getScene(sceneName, new HashMap<String, Object>());
	}

	public Scene getScene(String sceneName, Map<String, Object> parameters) {

		Injector childInjector = injector.createChildInjector(new AbstractModule() {

			@Override
			protected void configure() {
				bind(ImageManager.class).to(ImageManagerImpl.class).in(Singleton.class);
				bind(ComponentManager.class).in(Singleton.class);
				bind(AnimationManager.class).to(AnimationManagerImpl.class).in(Singleton.class);
				bind(World.class).in(Singleton.class);

				bind(TemplateProvider.class).toInstance(new GroovyTemplateProvider());
			}

		});

		GroovySceneBuilder groovySceneBuilder = childInjector.getInstance(GroovySceneBuilder.class);

		GroovySceneTemplate sceneTemplate = innerGetTemplate(sceneName, groovySceneBuilder);

		return sceneTemplate.instantiate(sceneName, parameters);
	}

	public class GroovySceneTemplate {

		Class<Script> scriptClass;

		GroovySceneBuilder groovySceneBuilder;

		public GroovySceneTemplate(Class<Script> scriptClass, GroovySceneBuilder groovySceneBuilder) {
			this.scriptClass = scriptClass;
			this.groovySceneBuilder = groovySceneBuilder;
		}

		public Scene instantiate(String sceneName, Map<String, Object> parameters) {
			Binding binding = new Binding();
			binding.setVariable("sceneName", sceneName);
			binding.setVariable("parameters", parameters);
			binding.setVariable("builder", groovySceneBuilder);

			return executeTemplate(this.scriptClass, binding);
		}

		private Scene executeTemplate(Class<Script> scriptClass, Binding binding) {
			try {
				Script script = scriptClass.newInstance();
				script.setBinding(binding);
				return (Scene) script.run();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}