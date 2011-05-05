package com.gemserk.componentsengine.resources.sounds;

public class NullSoundImpl implements Sound {

	@Override
	public void play() {
	}

	@Override
	public void play(float pitch, float volume) {
	}

	@Override
	public void stop() {
		
	}

	@Override
	public boolean isPlaying() {
		return false;
	}

	@Override
	public void loop() {
	}

}
