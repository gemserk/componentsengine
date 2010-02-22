package com.gemserk.componentsengine.builders;

import groovy.lang.Closure;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.messages.GenericMessage;
import com.gemserk.componentsengine.resources.AnimationManager;
import com.gemserk.componentsengine.resources.ImageManager;
import com.gemserk.componentsengine.utils.Container;
import com.gemserk.componentsengine.utils.Interval;
import com.google.inject.Inject;

public class BuilderUtils {
	
	Map<String, Object> custom = new HashMap<String, Object>();
	
	@Inject ImageManager imageManager;
	@Inject AnimationManager animationManager;
	
	
	public void addCustomUtil(String key, Object value)
	{
		custom.put(key, value);
	}

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

	public Container container(float current, float total) {
		return new Container(current, total);
	}
	
	public GenericMessage genericMessage(String id, Closure closure){
		GenericMessage genericMessage = new GenericMessage(id);
		closure.call(genericMessage);
		return genericMessage;
	}
	
	public ResourceUtils getResources(){
		return new ResourceUtils();
	}
	
	public class ResourceUtils {
		public Image image(String key) {
			return imageManager.getImage(key);
		}

		public Animation animation(String key) {
			return animationManager.getAnimation(key);
		}
	}
	

}