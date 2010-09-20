package com.gemserk.componentsengine.gamestates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import com.gemserk.componentsengine.builders.BuilderUtils;
import com.gemserk.componentsengine.input.CachedMonitorFactory;
import com.gemserk.componentsengine.input.MonitorFactory;
import com.gemserk.componentsengine.input.MonitorUpdater;
import com.gemserk.componentsengine.input.SlickMonitorFactory;
import com.gemserk.componentsengine.resources.AnimationManager;
import com.gemserk.componentsengine.resources.AnimationManagerImpl;
import com.gemserk.componentsengine.resources.ImageManager;
import com.gemserk.componentsengine.resources.ImageManagerImpl;
import com.gemserk.componentsengine.resources.PaulsSoundSystemSoundsManager;
import com.gemserk.componentsengine.resources.SoundsManager;
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
		bind(Input.class).toInstance(container.getInput());// SLICK
		bind(GameContainer.class).toInstance(container);// SLICK
		bind(Graphics.class).toInstance(container.getGraphics());// SLICK
		bind(StateBasedGame.class).toInstance(stateBasedGame);
		MonitorFactory realMonitorFactory = new SlickMonitorFactory();// SLICK
		requestInjection(realMonitorFactory);
		CachedMonitorFactory cachedMonitorFactory = new CachedMonitorFactory(realMonitorFactory);// GENERIC MONITOR
		bind(MonitorFactory.class).toInstance(cachedMonitorFactory);// GENERIC MONITOR
		bind(MonitorUpdater.class).toInstance(cachedMonitorFactory);// GENERIC MONITOR

		bind(ImageManager.class).to(ImageManagerImpl.class).in(Singleton.class);// SLICK
		bind(AnimationManager.class).to(AnimationManagerImpl.class).in(Singleton.class);// SLICK
		bind(SoundsManager.class).to(PaulsSoundSystemSoundsManager.class).in(Singleton.class);// SLICK
		
		bind(BuilderUtils.class).in(Singleton.class);
	}
}