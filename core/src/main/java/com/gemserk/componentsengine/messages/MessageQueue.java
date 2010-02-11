/**
 * 
 */
package com.gemserk.componentsengine.messages;


public interface MessageQueue {

	void enqueue(Message message);

	void processMessages();
	
}