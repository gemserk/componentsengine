package com.gemserk.componentsengine.effects;

import java.util.ArrayList;
import java.util.Collection;


public class ExplosionEffect {

	Collection<LineEffect> particles;

	public ExplosionEffect(ArrayList<LineEffect> particles) {
		this.particles = particles;
	}

	public void update(int delta) {
		for (LineEffect particle : particles) {
			particle.update(delta);
		}
	}

	public void render() {
		for (LineEffect particle : particles) {
			particle.render();
		}
	}
	
	public boolean isDone() {
		for (LineEffect particle : particles) {
			if (!particle.isDone())
				return false;
		}
		return true;
	}
	
}