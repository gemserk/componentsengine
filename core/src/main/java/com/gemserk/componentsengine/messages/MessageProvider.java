package com.gemserk.componentsengine.messages;

import java.util.Map;

import com.gemserk.componentsengine.properties.Property;
import com.gemserk.componentsengine.properties.SimpleProperty;
import com.gemserk.componentsengine.properties.SimpleProperyProvider;
import com.gemserk.componentsengine.utils.Pool;
import com.gemserk.componentsengine.utils.Pool.PoolObjectFactory;
import com.gemserk.componentsengine.utils.RandomAccess;
import com.gemserk.componentsengine.utils.RandomAccessMap;
import com.google.inject.Inject;

public class MessageProvider {

	Pool<Message> messagePool = new Pool<Message>(new PoolObjectFactory<Message>() {

		@Override
		public Message createObject() {
			return new Message();
		}
	}, 1000);

	@Inject
	SimpleProperyProvider simpleProperyProvider;

	public Message createMessage(String id) {
		Message message = messagePool.newObject();
		message.setId(id);
		// System.out.println("MESSAGES-NEW: "+ id + " - " + messagePool.size());
		return message;

	}

	public void free(Message message) {

		Map<String, Property<Object>> properties = message.getProperties();
		if (properties instanceof RandomAccess) {
			RandomAccess<SimpleProperty> propertiesRandom = (RandomAccess<SimpleProperty>) properties;
			for (int i = 0; i < propertiesRandom.size(); i++) {
				simpleProperyProvider.free(propertiesRandom.get(i));
			}
		}
		properties.clear();
		// String oldId = message.id;
		message.setId(null);
		messagePool.free(message);
		// System.out.println("MESSAGES-FREE: " + oldId + " - "+ messagePool.size());
	}

}
