package com.gemserk.componentsengine.resources;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryJavaSound;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

import com.gemserk.componentsengine.sounds.Sound;

public class PaulsSoundSystemSoundsManager implements SoundsManager {

	private static SoundSystem soundSystem;

	static {
		try {
			SoundSystemConfig.addLibrary(LibraryJavaSound.class);
			SoundSystemConfig.setCodec("wav", CodecWav.class);
			SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
			SoundSystemConfig.setSoundFilesPackage("");

			soundSystem = new SoundSystem();
			// soundSystem.backgroundMusic("backgrounhdmusic", "assets/sounds/backgroundmusic.mid", true);
		} catch (SoundSystemException e) {
			throw new RuntimeException("Sound system error", e);
		}
	}

	private Map<String, String> soundsMapping = new HashMap<String, String>();

	public PaulsSoundSystemSoundsManager() {

	}

	private class SoundPaulsSoundSystemImpl implements Sound {
				
		private final String key;
		
		private final boolean priority;
		
		private final boolean looped;

		private String soundSourceName;
		
		private SoundPaulsSoundSystemImpl(String key, boolean priority, boolean looped) {
			this.key = key;
			this.priority = priority;
			this.looped = looped;
		}

		@Override
		public void play(float pitch, float volume) {
			play();
		}

		@Override
		public void play() {
			soundSourceName = soundSystem.quickPlay(priority, soundsMapping.get(key), looped, 0, 0, 0, SoundSystemConfig.ATTENUATION_NONE, 0);
		}

		@Override
		public void stop() {
			if(soundSourceName == null)
				return;
			
			soundSystem.stop(soundSourceName);
		}
	}

	@Override
	public Sound getSound(final String key) {
		return getSound(key, false, false);
	}

	@Override
	public Sound getSound(String key, boolean priority, boolean toLoop) {
		return new SoundPaulsSoundSystemImpl(key, priority, toLoop);
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

}
