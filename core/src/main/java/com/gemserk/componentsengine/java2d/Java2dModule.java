package com.gemserk.componentsengine.java2d;

import com.gemserk.componentsengine.input.CachedMonitorFactory;
import com.gemserk.componentsengine.input.MonitorFactory;
import com.gemserk.componentsengine.input.MonitorUpdater;
import com.gemserk.componentsengine.java2d.input.Java2dMonitorFactory;
import com.gemserk.componentsengine.java2d.render.CurrentGraphicsProvider;
import com.gemserk.componentsengine.resources.images.ImageLoader;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class Java2dModule extends AbstractModule {

	private final Java2dWindow java2dWindow;

	public Java2dModule(Java2dWindow java2dWindow) {
		this.java2dWindow = java2dWindow;
	}

	@Override
	protected void configure() {
		bind(CurrentGraphicsProvider.class).in(Singleton.class);
		bind(ImageLoader.class).to(Java2dImageLoader.class).in(Singleton.class);
		bind(KeyboardInput.class).toInstance(java2dWindow.getKeyboardInput());
		bind(MouseInput.class).toInstance(java2dWindow.getMouseInput());
		
		MonitorFactory monitorFactory = new Java2dMonitorFactory();
		requestInjection(monitorFactory);
		CachedMonitorFactory cachedMonitorFactory = new CachedMonitorFactory(monitorFactory);
		bind(MonitorFactory.class).toInstance(cachedMonitorFactory);
		bind(MonitorUpdater.class).toInstance(cachedMonitorFactory);

	}
	
}