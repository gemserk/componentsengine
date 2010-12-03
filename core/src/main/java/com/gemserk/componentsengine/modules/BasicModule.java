package com.gemserk.componentsengine.modules;

import java.util.HashMap;
import java.util.Map;

import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.entities.EntityManager;
import com.gemserk.componentsengine.entities.Root;
import com.gemserk.componentsengine.game.GameLoop;
import com.gemserk.componentsengine.game.MessageReusingGameLoop;
import com.gemserk.componentsengine.messages.CopyOnWriteMessageDispatcher;
import com.gemserk.componentsengine.messages.MessageDispatcher;
import com.gemserk.componentsengine.messages.MessageProvider;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.messages.MessageQueueImpl;
import com.gemserk.componentsengine.messages.PoolReturningMessageQueue;
import com.gemserk.componentsengine.messages.messageBuilder.MessageBuilder;
import com.gemserk.componentsengine.messages.messageBuilder.MessageBuilderImpl;
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

		bind(CopyOnWriteMessageDispatcher.class).in(Singleton.class);
		bind(MessageDispatcher.class).to(CopyOnWriteMessageDispatcher.class);
		bind(MessageQueue.class).to(PoolReturningMessageQueue.class).in(Singleton.class);
		bind(MessageProvider.class).in(Singleton.class);
		bind(MessageBuilder.class).to(MessageBuilderImpl.class);//it is correct, in this case it is not singleton
		bind(GameLoop.class).to(MessageReusingGameLoop.class).in(Singleton.class);
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