package com.gemserk.componentsengine.effects;

import java.util.ArrayList;
import java.util.Collection;


public class ExplosionEffect {

	Collection<LineEffect> lineEffects;

	public ExplosionEffect(ArrayList<LineEffect> particles) {
		this.lineEffects = particles;
	}

	public void update(int delta) {
		for (LineEffect particle : lineEffects) {
			particle.update(delta);
		}
	}

	public void render() {
		for (LineEffect particle : lineEffects) {
			particle.render();
		}
	}
	
	public boolean isDone() {
		for (LineEffect particle : lineEffects) {
			if (!particle.isDone())
				return false;
		}
		return true;
	}
	
}