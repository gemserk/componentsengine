package com.gemserk.componentsengine.paulssoundsystem.resources.sounds;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import paulscode.sound.SoundSystem;

import com.gemserk.componentsengine.resources.sounds.Sound;
import com.gemserk.componentsengine.resources.sounds.SoundsManager;

public class PaulsSoundSystemSoundsManager implements SoundsManager {

	private static SoundSystem soundSystem;

	private Map<String, String> soundsMapping = new HashMap<String, String>();

	@Override
	public Sound getSound(final String key) {
		return getSound(key, false, false);
	}

	@Override
	public Sound getSound(String key, boolean priority, boolean toLoop) {
		return new SoundPaulsSoundSystemImpl(soundsMapping.get(key), priority, toLoop, soundSystem);
	}
	
	@Override
	public void addSound(String key, String url) {
		soundsMapping.put(key, url);
	}

	@Override
	public void addSounds(Map<String, String> soundsMap) {
		for (Entry<String, String> soundEntry : soundsMap.entrySet())
			addSound(soundEntry.getKey(), soundEntry.getValue());
	}

	public void init() {
		soundSystem = new SoundSystem();
	}

}
