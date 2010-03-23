package com.gemserk.componentsengine.resources;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class SoundsManagerSlickImpl implements SoundsManager {

	public static class SlickSoundProvider {

		public Sound load(String url) {
			try {
				return new Sound(url);
			} catch (SlickException e) {
				throw new RuntimeException("failed to get sound from " + url, e);
			}
		}

	}

	SlickSoundProvider soundProvider = new SlickSoundProvider();

	private Map<String, String> sounds = new LinkedHashMap<String, String>();
	
	public Sound getSound(String key) {
		if (!sounds.containsKey(key))
			throw new RuntimeException("sound " + key + " doesn't exist");
		return soundProvider.load(sounds.get(key));
	}

	public void addSound(String key, String url) {
		sounds.put(key, url);
		// to pre load sound when sound is registered and prevent failing when sound is used
		soundProvider.load(url);
	}

	@Override
	public void addSounds(Map<String, String> soundsMap) {
		for (Entry<String, String> soundEntry : soundsMap.entrySet())
			addSound(soundEntry.getKey(), soundEntry.getValue());
	}

}
