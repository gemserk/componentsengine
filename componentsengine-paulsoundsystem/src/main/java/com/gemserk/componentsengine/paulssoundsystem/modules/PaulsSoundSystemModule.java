package com.gemserk.componentsengine.paulssoundsystem.modules;

import com.gemserk.componentsengine.paulssoundsystem.resources.sounds.PaulsSoundSystemSoundsManager;
import com.gemserk.componentsengine.resources.sounds.SoundsManager;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public final class PaulsSoundSystemModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(PaulsSoundSystemSoundsManager.class).in(Singleton.class);
		bind(SoundsManager.class).to(PaulsSoundSystemSoundsManager.class).in(Singleton.class);
	}
}