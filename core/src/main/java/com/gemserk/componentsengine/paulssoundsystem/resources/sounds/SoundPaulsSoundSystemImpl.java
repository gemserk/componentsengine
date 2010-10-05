package com.gemserk.componentsengine.paulssoundsystem.resources.sounds;

import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;

import com.gemserk.componentsengine.resources.sounds.Sound;

class SoundPaulsSoundSystemImpl implements Sound {
			
	private final String filename;
	
	private final boolean priority;
	
	private final boolean looped;

	private String soundSourceName;

	private final SoundSystem soundSystem;
	
	public SoundPaulsSoundSystemImpl(String filename, boolean priority, boolean looped, SoundSystem soundSystem) {
		this.filename = filename;
		this.priority = priority;
		this.looped = looped;
		this.soundSystem = soundSystem;
	}

	@Override
	public void play(float pitch, float volume) {
		play();
	}

	@Override
	public void play() {
		internalPlay(looped);
	}

	public void internalPlay(boolean loop) {
		soundSourceName = soundSystem.quickPlay(priority, filename, loop, 0, 0, 0, SoundSystemConfig.ATTENUATION_NONE, 0);
	}

	@Override
	public void stop() {
		if(soundSourceName == null)
			return;
		
		soundSystem.stop(soundSourceName);
	}

	@Override
	public boolean isPlaying() {
		if(soundSourceName == null)
			return false;
		return soundSystem.playing(soundSourceName);
	}

	@Override
	public void loop() {
		internalPlay(true);
	}
}