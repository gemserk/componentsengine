package com.gemserk.componentsengine.resources;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class SoundsManagerSlickImpl implements SoundsManager {

	private Map<String, Sound> sounds = new LinkedHashMap<String, Sound>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gemserk.componentsengine.resources.SoundsManager#getSound(java.lang.String)
	 */
	public Sound getSound(String key) {
		if (!sounds.containsKey(key))
			throw new RuntimeException("sound " + key + " doesn't exist");
		return sounds.get(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gemserk.componentsengine.resources.SoundsManager#addSound(java.lang.String, java.lang.String)
	 */
	public void addSound(String key, String url) {
		sounds.put(key, soundProvider.load(url));
	}

	SlickSoundProvider soundProvider = new SlickSoundProvider();

	public static class SlickSoundProvider {

		public Sound load(String url) {
			try {
				return new Sound(url);
			} catch (SlickException e) {
				throw new RuntimeException("failed to get sound from " + url, e);
			}
		}

	}

	@Override
	public void addSounds(Map<String, String> soundsMap) {
		for (Entry<String, String> soundEntry : soundsMap.entrySet())
			addSound(soundEntry.getKey(), soundEntry.getValue());
	}

}
