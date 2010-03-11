package com.gemserk.componentsengine.effects;

public class LineEffectParticle {

	float source = 0f;

	float target;

	float p1 = 0;

	float p2 = 0;

	float totalTime;

	float currentTime = 0;

	float deltaTime = 0;

	float l = 10.0f;

	public LineEffectParticle(float target, float time) {
		this.target = target;
		this.totalTime = time;
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

		float length = l * deltaTime;

		float x = 1.0f * length;

		p1 = source + ((target - source) * currentTime / totalTime);
		p2 = p1 + x;
	}
}