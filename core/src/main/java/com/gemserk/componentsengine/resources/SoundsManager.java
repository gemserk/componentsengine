package com.gemserk.componentsengine.resources;

import java.util.Map;

import com.gemserk.componentsengine.sounds.Sound;

public interface SoundsManager {

	Sound getSound(String key);
	
	Sound getSound(String key, boolean priority, boolean toLoop);

	void addSound(String key, String url);
	
	void addSounds(Map<String, String> soundsMap);

}