package com.gemserk.componentsengine.messages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.gemserk.componentsengine.components.Component;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class MessageDispatcherImpl implements  MessageDispatcher {

	List<Component> components = new LinkedList<Component>();
	Queue<Message> messages = new LinkedList<Message>();

	Multimap<String, Component> componentsByMessageId = HashMultimap.create();

	@Override
	public void registerComponent(Component component) {
		String[] messageIds = component.getMessageIds();
		if (messageIds == null) {
			components.add(component);
		} else {
			for (String messageId : messageIds) {
				componentsByMessageId.put(messageId, component);
			}
		}
	}

	@Override
	public void unregisterComponent(Component component) {
		String[] messageIds = component.getMessageIds();
		if (messageIds == null) {
			components.remove(component);
		} else {
			for (String messageId : messageIds) {
				componentsByMessageId.remove(messageId, component);
			}
		}
	}

	@Override
	public void dispatch(Message message) {
		Collection<Component> componentsWithId = componentsByMessageId.get(message.getId());

		List<Component> handlers = new ArrayList<Component>(componentsWithId.size() + components.size());
		handlers.addAll(componentsWithId);
		handlers.addAll(components);

		for (Component component : handlers) {
			component.handleMessage(message);
		}
	}
}
