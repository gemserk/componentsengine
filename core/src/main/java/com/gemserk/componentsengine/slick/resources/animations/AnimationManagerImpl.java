package com.gemserk.componentsengine.slick.resources.animations;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Animation;

public class AnimationManagerImpl implements AnimationManager {
	Map<String, AnimationInstantiator> animations = new HashMap<String, AnimationInstantiator>();

	public void addAnimation(String key, AnimationInstantiator animation) {
		animations.put(key, animation);
	}

	public Animation getAnimation(String key) {
		return animations.get(key).instantiate();
	}
}
