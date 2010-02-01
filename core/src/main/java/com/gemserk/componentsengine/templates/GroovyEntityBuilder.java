/**
 * 
 */
package com.gemserk.componentsengine.templates;

import groovy.lang.Closure;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.components.ComponentManager;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.messages.GenericMessage;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.ReferenceProperty;
import com.gemserk.componentsengine.properties.SimpleProperty;
import com.gemserk.componentsengine.resources.AnimationManager;
import com.gemserk.componentsengine.resources.ImageManager;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class GroovyEntityBuilder{
	
	Entity entity;
	ComponentManager componentManager;
	String defaultEntityName;
	Map<String, Object> parameters;
	ImageManager imageManager;
	AnimationManager animationManager;	
	TemplateProvider templateProvider;
	private Injector injector;
	
	public GroovyEntityBuilder(String defaultEntityName, Map<String,Object> parameters) {
		this.defaultEntityName = defaultEntityName;
		this.parameters = parameters;
	}
	
	public GroovyEntityBuilder(Entity entity, Map<String,Object> parameters) {
		this.entity = entity;
		this.parameters = parameters;
		this.defaultEntityName = entity.getId();			
	}

	Entity entity(Closure closure){
		return this.entity(defaultEntityName,closure);
	}

	Entity entity(String id,Closure closure){
		if(entity==null)
			entity = new Entity(id);
		closure.setDelegate(this);
		closure.call();
		return entity;
	}
	
	void component(String idComponent){
		entity.addComponent(componentManager.getComponent(idComponent));
	}
	
	void component(Component component){
		injector.injectMembers(component);
		entity.addComponent(component);
	}
	
	void genericComponent(final Map<String,Object> parameters, final Closure closure){
		entity.addComponent(new Component((String)parameters.get("id")) {
			@Override
			public void handleMessage(Message message) {
				if (message instanceof GenericMessage) {
					GenericMessage genericMessage = (GenericMessage) message;
					if(!parameters.get("messageId").equals(genericMessage.getId()))
						return;
					
					closure.call(genericMessage);
				}
			}
		});
	}
	
	void property(String key, Object value){
		entity.addProperty(key, new SimpleProperty<Object>(value));
	}
	
	void propertyRef(String key, String referencedPropertyName){
		entity.addProperty(key,new ReferenceProperty<Object>(referencedPropertyName, entity));
	}
	
	
	void parent(String parent){
		EntityTemplate parentTemplate = this.templateProvider.getTemplate(parent);
		parentTemplate.apply(entity, parameters);
	}
	
	
	public Entity getEntity() {
		return entity;
	}
	
	public void tags(String... tags){
		this.tags(Arrays.asList(tags));
	}
	
	public void tags(List<String> tags){
		entity.getTags().addAll(tags);
	}
	
	public Image image(String key){
		return this.imageManager.getImage(key);
	}
	
	public Animation animation(String key){
		return this.animationManager.getAnimation(key);
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
	
	@Inject public void setInjector(Injector injector) {
		this.injector = injector;
	}
	
	@Inject public void setTemplateProvider(TemplateProvider templateProvider) {
		this.templateProvider = templateProvider;
	}
	
	
	
	
	
	
	
}