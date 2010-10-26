package com.gemserk.componentsengine.modules;

import java.util.HashMap;
import java.util.Map;

import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.entities.EntityManager;
import com.gemserk.componentsengine.entities.Root;
import com.gemserk.componentsengine.game.GameLoop;
import com.gemserk.componentsengine.game.GameLoopImpl;
import com.gemserk.componentsengine.messages.MessageDispatcher;
import com.gemserk.componentsengine.messages.MessageDispatcherImpl;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.render.RenderQueue;
import com.gemserk.componentsengine.render.RenderQueueImpl;
import com.gemserk.componentsengine.resources.images.ImageManager;
import com.gemserk.componentsengine.resources.images.ImageManagerImpl;
import com.gemserk.componentsengine.templates.JavaEntityTemplate;
import com.gemserk.componentsengine.templates.RegistrableTemplateProvider;
import com.gemserk.componentsengine.templates.TemplateProvider;
import com.gemserk.componentsengine.templates.TemplateProviderManager;
import com.gemserk.componentsengine.templates.TemplateProviderManagerImpl;
import com.gemserk.componentsengine.utils.annotations.BuilderUtils;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

public class BasicModule extends AbstractModule {
	@Override
	protected void configure() {

		bind(RenderQueueImpl.class).in(Singleton.class);
		bind(RenderQueue.class).to(RenderQueueImpl.class);

		bind(MessageDispatcherImpl.class).in(Singleton.class);
		bind(MessageDispatcher.class).to(MessageDispatcherImpl.class);
		bind(MessageQueue.class).to(MessageDispatcherImpl.class).in(Singleton.class);
		bind(GameLoop.class).to(GameLoopImpl.class).in(Singleton.class);
		bind(EntityManager.class).in(Singleton.class);

		Entity rootEntity = new Entity("root");
		bind(Entity.class).annotatedWith(Root.class).toInstance(rootEntity);

		bind(ImageManager.class).to(ImageManagerImpl.class).in(Singleton.class);
		bind(new TypeLiteral<Map<String, Object>>() {
		}).annotatedWith(BuilderUtils.class).toInstance(new HashMap<String, Object>());

		bind(TemplateProviderManagerImpl.class).in(Singleton.class);
		bind(TemplateProviderManager.class).to(TemplateProviderManagerImpl.class);
		bind(TemplateProvider.class).to(TemplateProviderManagerImpl.class);
		
		bind(RegistrableTemplateProvider.class).in(Singleton.class);
		bind(JavaEntityTemplate.class);
	}
}