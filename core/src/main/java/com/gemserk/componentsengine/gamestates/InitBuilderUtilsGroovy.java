package com.gemserk.componentsengine.gamestates;

import groovy.lang.Closure;

import com.gemserk.componentsengine.builders.BuilderUtils;
import com.gemserk.componentsengine.genericproviders.GenericProvider;
import com.gemserk.componentsengine.groovy.genericproviders.ValueFromClosure;
import com.gemserk.componentsengine.groovy.triggers.ClosureTrigger;
import com.gemserk.componentsengine.groovy.triggers.GroovySingleGenericMessageTrigger;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.triggers.NullTrigger;
import com.gemserk.componentsengine.triggers.Trigger;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class InitBuilderUtilsGroovy {
	@Inject
	Injector injector;

	public void config() {
		final BuilderUtils builderUtils = injector.getInstance(BuilderUtils.class);
		final MessageQueue messageQueue = injector.getInstance(MessageQueue.class);
		builderUtils.addCustomUtil("triggers", new Object() {

			public Trigger genericMessage(String messageId, Closure closure) {
				return new GroovySingleGenericMessageTrigger(messageId, messageQueue, closure);
			}

			public Trigger closureTrigger(Closure closure) {
				closure.setProperty("messageQueue", messageQueue);
				return new ClosureTrigger(closure);
			}

			public Trigger nullTrigger() {
				return new NullTrigger();
			}
		});

		builderUtils.addCustomUtil("genericprovider", new Object() {

			public GenericProvider provide(Closure closure) {
				return new ValueFromClosure(closure);
			}

		});
	}
}