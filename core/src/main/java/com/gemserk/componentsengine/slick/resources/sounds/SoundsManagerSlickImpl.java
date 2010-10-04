package com.gemserk.componentsengine.slick.resources.sounds;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.newdawn.slick.SlickException;

import com.gemserk.componentsengine.resources.sounds.Sound;
import com.gemserk.componentsengine.resources.sounds.SoundsManager;

public class SoundsManagerSlickImpl implements SoundsManager {

	public static class SlickSoundProvider {

		public Sound load(String url) {

			try {
				return new SlickSoundImpl(new org.newdawn.slick.Sound(url));
			} catch (SlickException e) {
				throw new RuntimeException("failed to get sound from " + url, e);
			}
		}

	}

	SlickSoundProvider soundProvider;

	private Map<String, String> sounds;

	public SoundsManagerSlickImpl() {
		soundProvider = new SlickSoundProvider();
		sounds = new LinkedHashMap<String, String>();
	}

	public Sound getSound(String key) {
		if (!sounds.containsKey(key))
			throw new RuntimeException("sound " + key + " doesn't exist");

		// return new NullSoundImpl();

		return soundProvider.load(sounds.get(key));
	}

	public void addSound(String key, String url) {
		sounds.put(key, url);
		// to pre load sound when sound is registered and prevent failing when sound is used
		// if (soundsEnabled && preloadSounds)
		soundProvider.load(url);
	}

	@Override
	public void addSounds(Map<String, String> soundsMap) {
		for (Entry<String, String> soundEntry : soundsMap.entrySet())
			addSound(soundEntry.getKey(), soundEntry.getValue());
	}

	@Override
	public Sound getSound(String key, boolean priority, boolean toLoop) {
		throw new UnsupportedOperationException("not implemented");
	}

}
