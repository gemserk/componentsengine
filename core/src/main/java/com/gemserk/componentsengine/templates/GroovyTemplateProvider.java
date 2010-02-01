package com.gemserk.componentsengine.templates;

import groovy.lang.GroovyClassLoader;
import groovy.lang.Script;

import com.gemserk.componentsengine.components.ComponentManager;
import com.gemserk.componentsengine.resources.AnimationManager;
import com.gemserk.componentsengine.resources.ImageManager;
import com.google.inject.Inject;

@SuppressWarnings("unchecked")
public class GroovyTemplateProvider implements TemplateProvider{

	private GroovyClassLoader groovyClassloader;
	private ComponentManager componentManager;
	private ImageManager imageManager;
	private AnimationManager animationManager;

	public GroovyTemplateProvider(){
		this(null,null,null);
	}
	
	public GroovyTemplateProvider(ComponentManager componentManager, ImageManager imageManager, AnimationManager animationManager) {
		this.componentManager = componentManager;
		this.imageManager = imageManager;
		this.animationManager = animationManager;
		groovyClassloader = new GroovyClassLoader();
		groovyClassloader.setShouldRecompile(true);
	}
	
	@Inject public void setAnimationManager(AnimationManager animationManager) {
		this.animationManager = animationManager;
	}
	
	@Inject public void setComponentManager(ComponentManager componentManager) {
		this.componentManager = componentManager;
	}
	
	@Inject public void setImageManager(ImageManager imageManager) {
		this.imageManager = imageManager;
	}
	
	
	@Override
	public EntityTemplate getTemplate(String name) {
		Class<Script> scriptClass;
		try {
			scriptClass = (Class<Script>) groovyClassloader.loadClass(name,true,false);
			return new GroovyEntityTemplate(scriptClass,componentManager,imageManager,animationManager,new CachingTemplateProvider(this));
		}
		catch (Exception e) {
			throw new TemplateNotFoundException("Unable to load template",e);
		}
	}
	
	public GroovyClassLoader getGroovyClassloader() {
		return groovyClassloader;
	}

}
