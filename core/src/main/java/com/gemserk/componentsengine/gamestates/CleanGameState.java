package com.gemserk.componentsengine.gamestates;


import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.entities.Root;
import com.gemserk.componentsengine.messages.ChildrenManagementMessageFactory;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.resources.PropertiesAnimationLoader;
import com.gemserk.componentsengine.resources.PropertiesImageLoader;
import com.gemserk.componentsengine.resources.SoundsManager;
import com.gemserk.componentsengine.templates.TemplateProvider;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class CleanGameState extends BasicGameState {

	protected StateBasedGame stateBasedGame;

	private final int id;

	protected final String iniScene;

	@Inject
	protected Injector injector;

	@Inject
	protected GameLoop gameLoop;

	@Inject
	MessageQueue messageQueue;

	@Inject
	private TemplateProvider templateProvider;

	@Inject @Root
	private Entity rootEntity;

	public CleanGameState(int id) {
		this(id, null);
	}

	public CleanGameState(int id, String iniScene) {
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

	public void images(String imagePropertiesFile) {
		long loadIni = System.currentTimeMillis();
		PropertiesImageLoader propertiesImageLoader = new PropertiesImageLoader(imagePropertiesFile);
		injector.injectMembers(propertiesImageLoader);
		propertiesImageLoader.load();
		long loadTime = System.currentTimeMillis() - loadIni;
		System.out.println("Loaded images (" + imagePropertiesFile + "): " + loadTime);
	}

	public void sounds(String soundsPropertiesFile) {
		SoundsManager soundsManager = injector.getInstance(SoundsManager.class);
		try {
			java.util.Properties soundsProperties = new java.util.Properties();
			InputStream soundsInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(soundsPropertiesFile);
			soundsProperties.load(soundsInputStream);
			soundsManager.addSounds((Map) soundsProperties);
		} catch (IOException e) {
			throw new RuntimeException("failed to load sounds from " + soundsPropertiesFile, e);
		}
	}

	public void animations(String animationPropertiesFile) {
		PropertiesAnimationLoader loader = new PropertiesAnimationLoader();
		injector.injectMembers(loader);
		loader.load(animationPropertiesFile);
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
		messageQueue.enqueue(ChildrenManagementMessageFactory.addEntity(entity, rootEntity));
	}

}