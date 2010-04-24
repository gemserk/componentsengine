package com.gemserk.componentsengine.sounds;

public class SlickSoundImpl implements Sound {
	
	private org.newdawn.slick.Sound slickSound;
	
	public SlickSoundImpl(org.newdawn.slick.Sound slickSound) {
		this.slickSound = slickSound;
	}

	@Override
	public void play() {
		slickSound.play();
	}

	@Override
	public void play(float pitch, float volume) {
		slickSound.play(pitch, volume);
	}

	@Override
	public void stop() {
		slickSound.stop();
	}

	@Override
	public boolean isPlaying() {
		return slickSound.playing();
	}

	@Override
	public void loop() {
		slickSound.loop();
	}

	
}
