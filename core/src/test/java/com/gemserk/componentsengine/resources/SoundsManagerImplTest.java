package com.gemserk.componentsengine.resources;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.newdawn.slick.Sound;

import com.gemserk.componentsengine.resources.SoundsManagerSlickImpl.SlickSoundProvider;


@RunWith(JMock.class)
public class SoundsManagerImplTest {

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
				oneOf(slickSoundProvider).load(soundUrl);
				will(returnValue(loadedSound));
			}
		});
		
		soundsManager.addSound(soundKey, soundUrl);
		
		Sound sound = soundsManager.getSound(soundKey);

		assertNotNull(sound);
		assertSame(loadedSound, sound);
	}

	
	@Test(expected=RuntimeException.class)
	public void shouldFailWhenSoundDoesntExist(){ 
		String soundKey = "soundkey";
		SoundsManager soundsManager = new SoundsManagerSlickImpl();
		soundsManager.getSound(soundKey);
	}
}
