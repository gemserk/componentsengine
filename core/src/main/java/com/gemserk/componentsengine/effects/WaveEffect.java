package com.gemserk.componentsengine.effects;

public class WaveEffect {

	public final WaveParticleEffect waveParticleEffect;

	public final WaveEffectRenderer waveEffectRenderer;

	public WaveEffect(WaveParticleEffect waveParticleEffect, WaveEffectRenderer waveEffectRenderer) {
		this.waveParticleEffect = waveParticleEffect;
		this.waveEffectRenderer = waveEffectRenderer;
	}

	public void update(int delta) {
		waveParticleEffect.update(delta);
	}

	public void render() {
		waveEffectRenderer.render(waveParticleEffect);
	}

	public void setPosition(float x, float y) {
		waveEffectRenderer.translation.set(x, y);
	}

}