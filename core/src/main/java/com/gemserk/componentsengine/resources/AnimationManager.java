package com.gemserk.componentsengine.resources;

import org.newdawn.slick.Animation;

public interface AnimationManager {

	void addAnimation(String key, AnimationInstantiator animationInstantiator);

	Animation getAnimation(String key);

}