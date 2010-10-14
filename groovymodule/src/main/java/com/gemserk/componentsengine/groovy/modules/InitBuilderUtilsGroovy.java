package com.gemserk.componentsengine.groovy.modules;

import groovy.lang.Closure;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.genericproviders.GenericProvider;
import com.gemserk.componentsengine.groovy.genericproviders.ValueFromClosure;
import com.gemserk.componentsengine.groovy.triggers.ClosureTrigger;
import com.gemserk.componentsengine.groovy.triggers.GroovySingleGenericMessageTrigger;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.properties.PropertiesMapBuilder;
import com.gemserk.componentsengine.triggers.NullTrigger;
import com.gemserk.componentsengine.triggers.Trigger;
import com.gemserk.componentsengine.utils.annotations.BuilderUtils;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

public class InitBuilderUtilsGroovy {
	public static class ProvidersUtils {
		public GenericProvider provide(Closure closure) {
			return new ValueFromClosure(closure);
		}
	}

	public static class TriggerUtils {
		private final MessageQueue messageQueue;

		private TriggerUtils(MessageQueue messageQueue) {
			this.messageQueue = messageQueue;
		}

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
	}
	
	public static class ComponentUtils {

		public Component genericComponent(final Map<String, Object> parameters, final Closure closure) {
			Object messageIdsCandidates = parameters.get("messageId");
			final Set<String> messageIds;
			if (messageIdsCandidates == null)
				messageIds = null;
			else {
				if (messageIdsCandidates instanceof Collection) {
					messageIds = Sets.newLinkedHashSet((Collection) messageIdsCandidates);
				} else {
					messageIds = Sets.newHashSet(messageIdsCandidates.toString());
				}
			}

			return new Component((String) parameters.get("id")) {

				@Override
				public void handleMessage(Message message) {

					if (messageIds !=null && !messageIds.contains(message.getId()))
						return;

					closure.setDelegate(this);
					closure.call(message);
				}

				@Override
				public Set<String> getMessageIds() {
					return messageIds;
				}

			};
		}
	}
	
	
	public static class MessageUtils {
		public Message delayedMessage(int delay, Message delayedMessage) {
			Message message = new Message("delayedMessage", new PropertiesMapBuilder().property("delay", delay).property("message", delayedMessage).build());
			return message;
		}
		
		
		public Message genericMessage(String id, Closure closure) {
			Message message = new Message(id);
			closure.call(message);
			return message;
		}

	}


	@Inject @BuilderUtils Map<String,Object> builderUtils;
	
	@Inject MessageQueue messageQueue;
	
	public void config() {
		builderUtils.put("triggers", new TriggerUtils(messageQueue));
		builderUtils.put("genericprovider", new ProvidersUtils());
		builderUtils.put("components", new ComponentUtils());
		builderUtils.put("messages", new MessageUtils());
	}
}