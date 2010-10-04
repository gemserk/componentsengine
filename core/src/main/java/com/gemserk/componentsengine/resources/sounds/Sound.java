package com.gemserk.componentsengine.resources.sounds;

public interface Sound {

	void play();
	
	void play(float pitch, float volume);
	
	void loop();
	
	void stop();
	
	boolean isPlaying();
	
}
