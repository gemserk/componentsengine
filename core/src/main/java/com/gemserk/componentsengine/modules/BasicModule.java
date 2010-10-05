package com.gemserk.componentsengine.modules;

import java.util.HashMap;
import java.util.Map;

import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.entities.EntityManager;
import com.gemserk.componentsengine.entities.Root;
import com.gemserk.componentsengine.game.GameLoop;
import com.gemserk.componentsengine.game.GameLoopImpl;
import com.gemserk.componentsengine.messages.MessageDispatcher;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.render.RenderQueue;
import com.gemserk.componentsengine.render.RenderQueueImpl;
import com.gemserk.componentsengine.resources.images.ImageManager;
import com.gemserk.componentsengine.resources.images.ImageManagerImpl;
import com.gemserk.componentsengine.utils.annotations.BuilderUtils;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class BasicModule extends AbstractModule {
	@Override
	protected void configure() {

		bind(RenderQueueImpl.class).in(Singleton.class);
		bind(RenderQueue.class).to(RenderQueueImpl.class);
		
		bind(MessageDispatcher.class).in(Singleton.class);
		bind(MessageQueue.class).to(MessageDispatcher.class).in(Singleton.class);
		bind(GameLoop.class).to(GameLoopImpl.class).in(Singleton.class);
		bind(EntityManager.class).in(Singleton.class);
		
		Entity rootEntity = new Entity("root");
		bind(Entity.class).annotatedWith(Root.class).toInstance(rootEntity);
		
		bind(ImageManager.class).to(ImageManagerImpl.class).in(Singleton.class);
		bind(Map.class).annotatedWith(BuilderUtils.class).toInstance(new HashMap<String, Object>());
	}
}