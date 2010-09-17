package com.gemserk.componentsengine.gamestates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import com.gemserk.componentsengine.builders.BuilderUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class InitBuilderUtilsSlick {
	@Inject
	Injector injector;

	@Inject
	GameContainer gameContainer;
	
	@Inject
	StateBasedGame stateBasedGame;
	
	public void config() {
		final BuilderUtils builderUtils = injector.getInstance(BuilderUtils.class);
		builderUtils.addCustomUtil("gameStateManager", stateBasedGame);
		builderUtils.addCustomUtil("gameContainer", gameContainer);
	}

}