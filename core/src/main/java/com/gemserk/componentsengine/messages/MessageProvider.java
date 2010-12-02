package com.gemserk.componentsengine.messages;

import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.utils.Pool;
import com.gemserk.componentsengine.utils.Pool.PoolObjectFactory;

public class MessageProvider {

	Pool<Message> messagePool = new Pool<Message>(new PoolObjectFactory<Message>() {

		@Override
		public Message createObject() {
			return new Message();
		}
	},1000);
	
	
	public Message createMessage(String id){
		Message message = messagePool.newObject();
		message.setId(id);
		return message;
	}
	
	public void free(Message message){
		message.getProperties().clear();
		message.setId(null);
		messagePool.free(message);
	}
	
	
}
