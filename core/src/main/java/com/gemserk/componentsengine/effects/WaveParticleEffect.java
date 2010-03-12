/**
 * 
 */
package com.gemserk.componentsengine.effects;

public class WaveParticleEffect {

	int vertexCount;

	float[] xVector;

	float[] aVector;

	float[] yVector;

	public WaveParticleEffect(int vertexCount, float[] xVector, float[] aVector) {
		this.vertexCount = vertexCount;

		this.xVector = xVector;
		this.aVector = aVector;

		yVector = new float[vertexCount];
	}

	public float waveFunction(float x, float a, float t) {
		double k = 1;
		double y = amplitude * a * Math.sin(k * x - w * t);
		return (float) y;
	}

	float time = 0;

	public double amplitude = 1;

	/**
	 * modificador del tiempo
	 */
	public double w = 1;

	public void update(int delta) {

		// f = 0.075 para que la onda se comporte aprox a 1 segundo para volver al lugar inicial
		time += (float) delta * 0.005f;

		for (int i = 0; i < vertexCount; i++) {
			yVector[i] = waveFunction(xVector[i], aVector[i], time);
		}

	}
}