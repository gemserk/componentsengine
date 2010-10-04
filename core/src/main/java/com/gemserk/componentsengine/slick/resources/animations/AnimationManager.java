package com.gemserk.componentsengine.slick.resources.animations;

import org.newdawn.slick.Animation;

public interface AnimationManager {

	void addAnimation(String key, AnimationInstantiator animationInstantiator);

	Animation getAnimation(String key);

}