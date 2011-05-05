package com.gemserk.componentsengine.messages;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.utils.RandomAccess;
import com.gemserk.componentsengine.utils.RandomAccessSet;

public class SimpleMessageDispatcher implements MessageDispatcher {

	
	protected static final Logger logger = LoggerFactory.getLogger(SimpleMessageDispatcher.class);
	
	
	Collection<Component> components = new RandomAccessSet<Component>();
	MyMultimap<String, Component> componentsByMessageId = new MyMultimap<String, Component>();
	
	public void registerComponent(Component component){
		String[] messageIds = component.getMessageIds();
		if(messageIds==null){
			components.add(component);
		}else{
			for (String messageId : messageIds) {
				componentsByMessageId.put(messageId, component);
			}
		}
	}
	
	public void unregisterComponent(Component component){
		String[] messageIds = component.getMessageIds();
		if(messageIds==null){
			components.remove(component);
		}else{
			for (String messageId : messageIds) {
				componentsByMessageId.remove(messageId, component);
			}
		}
	}
	
	
	@Override
	public void dispatch(Message message) {
		RandomAccess<Component> componentsWithId = (RandomAccess<Component>) componentsByMessageId.get(message.getId());
		
//		List<Component> handlers = new ArrayList<Component>(componentsWithId.size() + components.size());
//		handlers.addAll(componentsWithId);
//		handlers.addAll(components);

		
		for (int i = 0; i < componentsWithId.size(); i++) {
			componentsWithId.get(i).handleMessage(message);
		}
		
		RandomAccess<Component> generalComponents = (RandomAccess<Component>) components;
		for (int i = 0; i < generalComponents.size(); i++) {
			generalComponents.get(i).handleMessage(message);
		}
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
				innerCollection = new RandomAccessSet<V>();
				map.put(key, innerCollection);
			}
			return innerCollection;
		}
		
		
	}

	
}
