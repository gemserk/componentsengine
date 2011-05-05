package com.gemserk.componentsengine.resources.sounds;

import java.util.Map;


public interface SoundsManager {

	Sound getSound(String key);
	
	Sound getSound(String key, boolean priority, boolean toLoop);

	void addSound(String key, String url);
	
	void addSounds(Map<String, String> soundsMap);

}