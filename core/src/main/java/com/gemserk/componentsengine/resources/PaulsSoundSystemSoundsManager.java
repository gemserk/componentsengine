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
		} catch (SoundSystemException e) {
			throw new RuntimeException("Sound system error",e);
		}
	}
	
	private Map<String,String> soundsMapping = new HashMap<String, String>();

	public PaulsSoundSystemSoundsManager() {
		
	}
	
	@Override
	public Sound getSound(final String key) {
		return new Sound() {
			
			@Override
			public void play(float pitch, float volume) {
				play();	
			}
			
			@Override
			public void play() {
				soundSystem.quickPlay(false, soundsMapping.get(key), false, 0,0,0, SoundSystemConfig.ATTENUATION_NONE,0);
			}
		};
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
