package com.gemserk.componentsengine.slick.effects;

public class LineEffectParticle {

	float source = 0f;

	float target;

	float p1 = 0;

	float p2 = 0;

	float totalTime;

	float currentTime = 0;

	float deltaTime = 0;

	float length;

	public LineEffectParticle(float target, float time, float length) {
		this.target = target;
		this.totalTime = time;
		this.length = length;
	}

	public float getDeltaTime() {
		return deltaTime;
	}

	public boolean isDone() {
		return currentTime == totalTime;
	}

	public void update(int delta) {

		currentTime += delta;

		if (currentTime > totalTime)
			currentTime = totalTime;

		deltaTime = (totalTime - currentTime) / totalTime;

		float l = length * deltaTime;

		float x = 1.0f * l;

		p1 = source + ((target - source) * currentTime / totalTime);
		p2 = p1 + x;
	}
}