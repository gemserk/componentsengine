package com.gemserk.componentsengine.paulssoundsystem.modules;

import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryJavaSound;

import com.gemserk.componentsengine.paulssoundsystem.resources.sounds.PaulsSoundSystemSoundsManager;
import com.google.inject.Inject;

public class InitPaulsSoundSystem {
	
	@Inject PaulsSoundSystemSoundsManager soundsManager;
	
	public void config() {
		try {
			SoundSystemConfig.addLibrary(LibraryJavaSound.class);
			SoundSystemConfig.setCodec("wav", CodecWav.class);
			SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
			SoundSystemConfig.setSoundFilesPackage("");

			
			// soundSystem.backgroundMusic("backgrounhdmusic", "assets/sounds/backgroundmusic.mid", true);
		} catch (SoundSystemException e) {
			throw new RuntimeException("Sound system error", e);
		}
		
		soundsManager.init();
	}
	
}