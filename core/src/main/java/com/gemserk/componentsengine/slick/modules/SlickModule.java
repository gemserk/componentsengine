package com.gemserk.componentsengine.slick.modules;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import com.gemserk.componentsengine.builders.BuilderUtils;
import com.gemserk.componentsengine.input.CachedMonitorFactory;
import com.gemserk.componentsengine.input.MonitorFactory;
import com.gemserk.componentsengine.input.MonitorUpdater;
import com.gemserk.componentsengine.resources.AnimationManager;
import com.gemserk.componentsengine.resources.AnimationManagerImpl;
import com.gemserk.componentsengine.resources.ImageLoader;
import com.gemserk.componentsengine.resources.PaulsSoundSystemSoundsManager;
import com.gemserk.componentsengine.resources.SlickImageLoader;
import com.gemserk.componentsengine.resources.SoundsManager;
import com.gemserk.componentsengine.slick.input.SlickMonitorFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public final class SlickModule extends AbstractModule {
	private final GameContainer container;
	private final StateBasedGame stateBasedGame;

	public SlickModule(GameContainer container, StateBasedGame stateBasedGame) {
		this.container = container;
		this.stateBasedGame = stateBasedGame;
	}

	@Override
	protected void configure() {
		bind(Input.class).toInstance(container.getInput());
		bind(GameContainer.class).toInstance(container);
		bind(Graphics.class).toInstance(container.getGraphics());
		bind(StateBasedGame.class).toInstance(stateBasedGame);
		
		MonitorFactory realMonitorFactory = new SlickMonitorFactory();
		requestInjection(realMonitorFactory);
		CachedMonitorFactory cachedMonitorFactory = new CachedMonitorFactory(realMonitorFactory);
		bind(MonitorFactory.class).toInstance(cachedMonitorFactory);
		bind(MonitorUpdater.class).toInstance(cachedMonitorFactory);

		bind(AnimationManager.class).to(AnimationManagerImpl.class).in(Singleton.class);
		bind(SoundsManager.class).to(PaulsSoundSystemSoundsManager.class).in(Singleton.class);
		
		bind(BuilderUtils.class).in(Singleton.class);
		
		bind(ImageLoader.class).to(SlickImageLoader.class).in(Singleton.class);
	}
}