package com.gemserk.componentsengine.templates;

import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.Script;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

import com.gemserk.componentsengine.components.ComponentManager;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.properties.Property;
import com.gemserk.componentsengine.properties.ReferenceProperty;
import com.gemserk.componentsengine.properties.SimpleProperty;
import com.gemserk.componentsengine.resources.AnimationManager;
import com.gemserk.componentsengine.resources.ImageManager;

public class GroovyEntityTemplate implements EntityTemplate {

	Class<Script> scriptClass;
	private final ComponentManager componentManager;
	private final TemplateProvider templateProvider;
	private final ImageManager imageManager;
	private final AnimationManager animationManager;
	public GroovyEntityTemplate(Class<Script> scriptClass, final ComponentManager componentManager, ImageManager imageManager, AnimationManager animationManager, TemplateProvider templateProvider) {
		this.scriptClass = scriptClass;
		this.componentManager = componentManager;
		this.animationManager = animationManager;
		this.templateProvider = templateProvider;
		this.imageManager = imageManager;
	}

	@Override
	public Entity instantiate(String entityName) {
		return instantiate(entityName, new HashMap<String, Object>());
	}
	
	@Override
	public Entity instantiate(String entityName, Map<String, Object> parameters) {
		Binding binding = new Binding();
		binding.setVariable("entityName", entityName);
		binding.setVariable("parameters", parameters);
		binding.setVariable("builder", new EntityBuilder(entityName, componentManager,imageManager,animationManager,parameters));
		
		return executeTemplate(this.scriptClass,binding);
	}

	@Override
	public Entity apply(Entity entity) {
		// I suppose we have to use null instead.
		return apply(entity, new HashMap<String, Object>());
	}

	
	@Override
	public Entity apply(Entity entity, Map<String, Object> parameters) {
		Binding binding = new Binding();
		binding.setVariable("entityName", entity.getId());
		binding.setVariable("parameters", parameters);
		binding.setVariable("builder", new EntityBuilder(entity, componentManager,imageManager,animationManager, parameters));
		
		return executeTemplate(this.scriptClass,binding);
	}

	private Entity executeTemplate(Class<Script> scriptClass,Binding binding) {
		try {
			Script script = scriptClass.newInstance();
			script.setBinding(binding);
			return (Entity) script.run();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	class EntityBuilder{
		
		Entity entity;
		ComponentManager componentManager;
		private final String defaultEntityName;
		private final Map<String, Object> parameters;
		private final ImageManager imageManager;
		private final AnimationManager animationManager;		
		
		public EntityBuilder(String defaultEntityName, ComponentManager componentManager, ImageManager imageManager, AnimationManager animationManager, Map<String,Object> parameters) {
			this.defaultEntityName = defaultEntityName;
			this.componentManager = componentManager;
			this.imageManager = imageManager;
			this.animationManager = animationManager;
			this.parameters = parameters;
		}
		
		public EntityBuilder(Entity entity, ComponentManager componentManager, ImageManager imageManager, AnimationManager animationManager, Map<String,Object> parameters) {
			this.entity = entity;
			this.componentManager = componentManager;
			this.imageManager = imageManager;
			this.animationManager = animationManager;
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
		
		void property(String key, Object value){
			entity.addProperty(key, new SimpleProperty<Object>(value));
		}
		
		void propertyRef(String key, String referencedPropertyName){
			entity.addProperty(key,new ReferenceProperty<Object>(referencedPropertyName, entity));
		}
		
		
		void parent(String parent){
			EntityTemplate parentTemplate = templateProvider.getTemplate(parent);
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
	}
	
	
//	class Utils {
//		Object properties = new Object(){
//			public Property<Object> ref(String referencedPropertyName){
//				return new ReferenceProperty<Object>(referencedPropertyName, holder)
//			}
//		};
//	}

}
