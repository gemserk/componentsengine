package com.gemserk.componentsengine.slick.modules;

import com.gemserk.componentsengine.resources.sounds.SoundsManager;
import com.gemserk.componentsengine.slick.resources.sounds.SoundsManagerSlickImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public final class SlickSoundSystemModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(SoundsManager.class).to(SoundsManagerSlickImpl.class).in(Singleton.class);
	}
}