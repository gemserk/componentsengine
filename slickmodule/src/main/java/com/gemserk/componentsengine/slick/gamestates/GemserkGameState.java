package com.gemserk.componentsengine.slick.gamestates;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.entities.Root;
import com.gemserk.componentsengine.game.GameLoop;
import com.gemserk.componentsengine.messages.ChildrenManagementMessageFactory;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.templates.TemplateProvider;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class GemserkGameState extends BasicGameState {

	protected StateBasedGame stateBasedGame;

	private final int id;

	protected final String iniScene;

	@Inject
	protected Injector injector;

	@Inject
	protected GameLoop gameLoop;

	@Inject
	MessageQueue messageQueue;
	
	@Inject ChildrenManagementMessageFactory childrenManagementMessageFactory;

	@Inject
	private TemplateProvider templateProvider;

	@Inject @Root
	private Entity rootEntity;

	public GemserkGameState(int id) {
		this(id, null);
	}

	public GemserkGameState(int id, String iniScene) {
		super();
		this.id = id;
		this.iniScene = iniScene;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public void init(final GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		this.stateBasedGame = stateBasedGame;
		onInit();
		initGame();
	}

	public void onInit() {

	}

	private void initGame() {
		if (this.iniScene != null)
			loadScene(this.iniScene);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		gameLoop.render();
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		gameLoop.update(delta);
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		messageQueue.enqueue(new Message("enterState"));
		messageQueue.processMessages();
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		messageQueue.enqueue(new Message("leaveState"));
		messageQueue.processMessages();
	}

	public void loadScene(String sceneName) {
		Entity entity = templateProvider.getTemplate(sceneName).instantiate("scene");
		messageQueue.enqueue(childrenManagementMessageFactory.addEntity(entity, rootEntity));
	}

}