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
//		System.out.println("MESSAGES-NEW: "+ id + " - " + messagePool.size());
		return message;
		
	}
	
	public void free(Message message){
		message.getProperties().clear();
//		String oldId = message.id;
		message.setId(null);
		messagePool.free(message);
//		System.out.println("MESSAGES-FREE: " + oldId + " - "+ messagePool.size());
	}
	
	
}
