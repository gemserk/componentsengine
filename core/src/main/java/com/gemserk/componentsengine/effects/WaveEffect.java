package com.gemserk.componentsengine.effects;

public class WaveEffect {

	public final WaveParticleEffect waveParticleEffect;

	public final WaveEffectRenderer waveEffectRenderer;

	int currentTime;

	int totalTime;

	public WaveEffect(WaveParticleEffect waveParticleEffect, WaveEffectRenderer waveEffectRenderer) {
		this.waveParticleEffect = waveParticleEffect;
		this.waveEffectRenderer = waveEffectRenderer;
	}

	public void update(int delta) {

		currentTime -= delta;

		if (currentTime <= 0)
			currentTime = 0;

		waveParticleEffect.update(delta);
	}

	public boolean isDone() {
		return currentTime <= 0;
	}

	public void render() {
		waveEffectRenderer.render(waveParticleEffect);
	}

	public void setPosition(float x, float y) {
		waveEffectRenderer.translation.set(x, y);
	}

}