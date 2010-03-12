package com.gemserk.componentsengine.effects;

import org.newdawn.slick.Color;

public class LineEffect {

	LineEffectParticle lineParticleEffect;

	LineEffectOpenGlRenderer lineParticleEffectRenderer;

	Color startColor;
	
	Color endColor;
	
	Color currentColor;
	
	int currentTime;
	
	int totalTime;

	public LineEffect(LineEffectParticle lineParticleEffect, LineEffectOpenGlRenderer lineParticleEffectRenderer, int time, Color startColor, Color endColor) {
		this.lineParticleEffect = lineParticleEffect;
		this.lineParticleEffectRenderer = lineParticleEffectRenderer;
		this.startColor = startColor;
		this.endColor = endColor;
		this.currentColor = new Color(startColor);
		this.totalTime = time;
		this.currentTime = time;
	}

	public void update(int delta) {
		lineParticleEffect.update(delta);
		
		currentTime -= delta;
		if (currentTime< 0)
			currentTime = 0;
		
		float f = (float)currentTime /(float)totalTime;
		
		currentColor.r = startColor.r * f + endColor.r * (1-f);
		currentColor.g = startColor.g * f + endColor.g * (1-f);
		currentColor.b = startColor.b * f + endColor.b * (1-f);
		currentColor.a = startColor.a * f + endColor.a * (1-f);
	}

	public void render() {
		lineParticleEffectRenderer.render(lineParticleEffect, currentColor);
	}

	public boolean isDone() {
		return lineParticleEffect.isDone();
	}

}