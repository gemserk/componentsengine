package com.gemserk.componentsengine.resources;

import java.util.Map;

import org.newdawn.slick.Sound;

public interface SoundsManager {

	Sound getSound(String key);

	void addSound(String key, String url);
	
	void addSounds(Map<String, String> soundsMap);

}