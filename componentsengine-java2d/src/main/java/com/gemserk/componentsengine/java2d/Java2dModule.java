package com.gemserk.componentsengine.java2d;

import com.gemserk.componentsengine.input.CachedMonitorFactory;
import com.gemserk.componentsengine.input.MonitorFactory;
import com.gemserk.componentsengine.input.MonitorUpdater;
import com.gemserk.componentsengine.java2d.input.Java2dMonitorFactory;
import com.gemserk.componentsengine.java2d.input.KeyboardInput;
import com.gemserk.componentsengine.java2d.input.MouseInput;
import com.gemserk.componentsengine.java2d.render.CurrentGraphicsProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class Java2dModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(CurrentGraphicsProvider.class).in(Singleton.class);
		bind(KeyboardInput.class).in(Singleton.class);
		bind(MouseInput.class).in(Singleton.class);
		MonitorFactory monitorFactory = new Java2dMonitorFactory();
		requestInjection(monitorFactory);
		CachedMonitorFactory cachedMonitorFactory = new CachedMonitorFactory(monitorFactory);
		bind(MonitorFactory.class).toInstance(cachedMonitorFactory);
		bind(MonitorUpdater.class).toInstance(cachedMonitorFactory);
	}

}