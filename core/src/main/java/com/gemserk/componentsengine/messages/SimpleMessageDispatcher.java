package com.gemserk.componentsengine.messages;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.MessageDispatcher;
import com.gemserk.componentsengine.messages.MessageQueue;

public class SimpleMessageDispatcher implements MessageDispatcher, MessageQueue {

	
	protected static final Logger logger = LoggerFactory.getLogger(SimpleMessageDispatcher.class);
	
	
	Collection<Component> components = new CopyOnWriteArraySet<Component>();
	Queue<Message> messages = new LinkedList<Message>();
	
	MyMultimap<String, Component> componentsByMessageId = new MyMultimap<String, Component>();
	
	public void registerComponent(Component component){
		Set<String> messageIds = component.getMessageIds();
		if(messageIds==null){
			components.add(component);
		}else{
			for (String messageId : messageIds) {
				componentsByMessageId.put(messageId, component);
			}
		}
	}
	
	public void unregisterComponent(Component component){
		Set<String> messageIds = component.getMessageIds();
		if(messageIds==null){
			components.remove(component);
		}else{
			for (String messageId : messageIds) {
				componentsByMessageId.remove(messageId, component);
			}
		}
	}
	
	
	@Override
	public void enqueue(Message message) {
		dispatch(message);
	}

	@Override
	public void dispatch(Message message) {
		Collection<Component> componentsWithId = componentsByMessageId.get(message.getId());
		
//		List<Component> handlers = new ArrayList<Component>(componentsWithId.size() + components.size());
//		handlers.addAll(componentsWithId);
//		handlers.addAll(components);

		for (Component component : componentsWithId) {
			component.handleMessage(message);
		}
		for (Component component : components) {
			component.handleMessage(message);
		}
	}

	@Override
	public void processMessages() {
		while (!messages.isEmpty()) {
			Message message = messages.poll();
			enqueue(message);
		}
	}

	@Override
	public void enqueueDelay(Message message) {
		messages.add(message);
	}
	
	public class MyMultimap<K,V> {

		Map<K,Collection<V>> map = new HashMap<K, Collection<V>>();
		
		
		
		
		public void put(K key, V value) {
			Collection<V> innerCollection = getInnerCollection(key);
			innerCollection.add(value);
		}


		public void remove(K key, V value) {
			Collection<V> innerCollection = getInnerCollection(key);
			innerCollection.remove(value);
		}
		
		public Collection<V> get(K key) {
			return getInnerCollection(key);
		}


		private Collection<V> getInnerCollection(K key) {
			Collection<V> innerCollection = map.get(key);
			if(innerCollection == null){
				innerCollection = new HashSet();
				map.put(key, innerCollection);
			}
			return innerCollection;
		}
		
		
	}

	
}
