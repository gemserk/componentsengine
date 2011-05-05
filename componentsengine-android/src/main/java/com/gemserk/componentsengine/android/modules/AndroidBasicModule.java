package com.gemserk.componentsengine.android.modules;

import com.gemserk.componentsengine.android.MessageIntermediator;
import com.gemserk.componentsengine.android.render.CurrentGL;
import com.gemserk.componentsengine.android.render.TextureLoader;
import com.gemserk.componentsengine.input.MonitorUpdater;
import com.gemserk.componentsengine.input.NullMonitorUpdater;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class AndroidBasicModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(MonitorUpdater.class).toInstance(new NullMonitorUpdater());

		bind(CurrentGL.class).in(Singleton.class);
		bind(TextureLoader.class).in(Singleton.class);
		bind(MessageIntermediator.class).in(Singleton.class);
	}
}