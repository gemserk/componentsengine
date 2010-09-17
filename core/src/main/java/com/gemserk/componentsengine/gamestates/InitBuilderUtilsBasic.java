package com.gemserk.componentsengine.gamestates;

import com.gemserk.componentsengine.builders.BuilderUtils;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.templates.TemplateProvider;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class InitBuilderUtilsBasic {
	@Inject
	Injector injector;

	public void config() {
		final BuilderUtils builderUtils = injector.getInstance(BuilderUtils.class);
		final MessageQueue messageQueue = injector.getInstance(MessageQueue.class);
		builderUtils.addCustomUtil("templateProvider", injector.getInstance(TemplateProvider.class));
		builderUtils.addCustomUtil("messageQueue", messageQueue);
				
	}
}