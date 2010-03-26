package com.gemserk.componentsengine.resources;

import static org.junit.Assert.assertNotNull;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gemserk.componentsengine.resources.SoundsManagerSlickImpl.SlickSoundProvider;
import com.gemserk.componentsengine.sounds.Sound;


@RunWith(JMock.class)
public class SoundsManagerSlickImplTest {

	Mockery mockery = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	
	@Test
	public void shouldGetSound(){ 
		final String soundUrl = "soundurl";
		String soundKey = "soundkey";
		
		final SlickSoundProvider slickSoundProvider = mockery.mock(SlickSoundProvider.class);
		final Sound loadedSound = mockery.mock(Sound.class);
		
		SoundsManagerSlickImpl soundsManager = new SoundsManagerSlickImpl();
		soundsManager.soundProvider = slickSoundProvider;
		
		mockery.checking(new Expectations() {
			{
				ignoring(slickSoundProvider).load(soundUrl);
				will(returnValue(loadedSound));
			}
		});
		
		soundsManager.addSound(soundKey, soundUrl);
		
		Sound sound = soundsManager.getSound(soundKey);

		assertNotNull(sound);
	}

	
	@Test(expected=RuntimeException.class)
	public void shouldFailWhenSoundDoesntExist(){ 
		String soundKey = "soundkey";
		SoundsManager soundsManager = new SoundsManagerSlickImpl();
		soundsManager.getSound(soundKey);
	}
}
