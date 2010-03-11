package com.gemserk.componentsengine.effects;


public class LineEffect {

	LineEffectParticle lineParticleEffect;

	LineEffectOpenGlRenderer lineParticleEffectRenderer;

	public LineEffect(LineEffectParticle lineParticleEffect, LineEffectOpenGlRenderer lineParticleEffectRenderer) {
		this.lineParticleEffect = lineParticleEffect;
		this.lineParticleEffectRenderer = lineParticleEffectRenderer;
	}

	public void update(int delta) {
		lineParticleEffect.update(delta);
	}

	public void render() {
		lineParticleEffectRenderer.render(lineParticleEffect);
	}

	public boolean isDone() {
		return lineParticleEffect.isDone();
	}

}