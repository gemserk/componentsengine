package com.gemserk.componentsengine.gamestates;

import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.entities.EntityManager;
import com.gemserk.componentsengine.entities.Root;
import com.gemserk.componentsengine.messages.MessageDispatcher;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.render.Renderer;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class BasicModule extends AbstractModule {
	@Override
	protected void configure() {

		bind(Renderer.class).in(Singleton.class);
		bind(MessageDispatcher.class).in(Singleton.class);
		bind(MessageQueue.class).to(MessageDispatcher.class).in(Singleton.class);
		bind(GameLoop.class).to(GameLoopImpl.class).in(Singleton.class);
		bind(EntityManager.class).in(Singleton.class);
		
		Entity rootEntity = new Entity("root");
		bind(Entity.class).annotatedWith(Root.class).toInstance(rootEntity);
	}
}