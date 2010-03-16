package com.gemserk.componentsengine.gamestates;

import groovy.lang.Closure;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.gemserk.componentsengine.builders.BuilderUtils;
import com.gemserk.componentsengine.components.ChildrenManagementComponent;
import com.gemserk.componentsengine.components.MessageHandler;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.entities.Root;
import com.gemserk.componentsengine.game.Game;
import com.gemserk.componentsengine.genericproviders.GenericProvider;
import com.gemserk.componentsengine.genericproviders.ValueFromClosure;
import com.gemserk.componentsengine.input.MonitorFactory;
import com.gemserk.componentsengine.input.SlickMonitorFactory;
import com.gemserk.componentsengine.messages.GenericMessage;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.messages.MessageQueueImpl;
import com.gemserk.componentsengine.messages.SlickRenderMessage;
import com.gemserk.componentsengine.messages.UpdateMessage;
import com.gemserk.componentsengine.resources.AnimationManager;
import com.gemserk.componentsengine.resources.AnimationManagerImpl;
import com.gemserk.componentsengine.resources.ImageManager;
import com.gemserk.componentsengine.resources.ImageManagerImpl;
import com.gemserk.componentsengine.resources.PropertiesImageLoader;
import com.gemserk.componentsengine.templates.CachedScriptProvider;
import com.gemserk.componentsengine.templates.GroovyScriptProvider;
import com.gemserk.componentsengine.templates.GroovyScriptProviderImpl;
import com.gemserk.componentsengine.templates.GroovyTemplateProvider;
import com.gemserk.componentsengine.templates.TemplateProvider;
import com.gemserk.componentsengine.triggers.ClosureTrigger;
import com.gemserk.componentsengine.triggers.GroovySingleGenericMessageTrigger;
import com.gemserk.componentsengine.triggers.Trigger;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;

public class GemserkGameState extends BasicGameState {

	protected StateBasedGame stateBasedGame;

	protected MessageQueue messageQueue;

	protected Game game;

	private final int id;

	protected final String iniScene;

	protected Injector injector;

	public GemserkGameState(int id) {
		this(id,null);
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

		final Entity rootEntity = new Entity("root");
		rootEntity.addComponent(new ChildrenManagementComponent("childrenManagementComponent"));

		injector = Guice.createInjector(new AbstractModule() {
			

			@Override
			protected void configure() {
				bind(Input.class).toInstance(container.getInput());
				bind(Game.class).in(Singleton.class);
				bind(GameContainer.class).toInstance(container);

				bind(GroovyScriptProvider.class).toInstance(new CachedScriptProvider(new GroovyScriptProviderImpl()));
				bind(MonitorFactory.class).to(SlickMonitorFactory.class).in(Singleton.class);
				bind(MessageHandler.class).to(Game.class).in(Singleton.class);
				bind(MessageQueue.class).to(MessageQueueImpl.class).in(Singleton.class);

				bind(BuilderUtils.class).in(Singleton.class);
				bind(Entity.class).annotatedWith(Root.class).toInstance(rootEntity);

				bind(ImageManager.class).to(ImageManagerImpl.class).in(Singleton.class);
				bind(AnimationManager.class).to(AnimationManagerImpl.class).in(Singleton.class);

				bind(TemplateProvider.class).toInstance(new GroovyTemplateProvider());
			}
		});

		messageQueue = injector.getInstance(MessageQueue.class);
		game = injector.getInstance(Game.class);
		final BuilderUtils builderUtils = injector.getInstance(BuilderUtils.class);

		builderUtils.addCustomUtil("templateProvider", injector.getInstance(TemplateProvider.class));
		builderUtils.addCustomUtil("game", injector.getInstance(Game.class));
		builderUtils.addCustomUtil("messageQueue", messageQueue);
		// slick utils
		builderUtils.addCustomUtil("gameStateManager", stateBasedGame);
		builderUtils.addCustomUtil("gameContainer", container);

		builderUtils.addCustomUtil("triggers", new Object() {

			public Trigger genericMessage(String messageId, Closure closure) {
				return new GroovySingleGenericMessageTrigger(messageId, messageQueue, closure);
			}
			
			public Trigger closureTrigger(Closure closure){
				closure.setProperty("messageQueue", messageQueue);
				return new ClosureTrigger(closure);
			}
		});

		builderUtils.addCustomUtil("genericprovider", new Object() {

			public GenericProvider provide(Closure closure) {
				return new ValueFromClosure(closure);
			}

		});
		
		onInit();
		initGame();
	}

	public void onInit(){
		
	}
	
	
	private void initGame() {
		if(this.iniScene!=null)
			loadScene(this.iniScene);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setBackground(new Color(0.15f, 0.15f, 0.15f));
		messageQueue.enqueue(new SlickRenderMessage(g));
		messageQueue.processMessages();
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		messageQueue.enqueue(new UpdateMessage(delta));
		messageQueue.processMessages();
	}

	public void images(Injector injector, String imagePropertiesFile) {
		long loadIni = System.currentTimeMillis();
		PropertiesImageLoader propertiesImageLoader = new PropertiesImageLoader(imagePropertiesFile);
		injector.injectMembers(propertiesImageLoader);
		propertiesImageLoader.load();
		long loadTime = System.currentTimeMillis() - loadIni;
		System.out.println("Loaded images (" + imagePropertiesFile + "): " + loadTime);
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		messageQueue.enqueue(new GenericMessage("enterState"));
		messageQueue.processMessages();
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		messageQueue.enqueue(new GenericMessage("leaveState"));
		messageQueue.processMessages();
	}
	
	public void loadScene(String sceneName){
		game.loadScene(sceneName);
	}

}