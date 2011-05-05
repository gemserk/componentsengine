package com.gemserk.componentsengine.jbullet;

import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.InternalTickCallback;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.properties.PropertiesMapBuilder;
import com.google.inject.Inject;

/**
 * Enqueues a message physicsUpdate to components engine whenever internalTick callback is called.
 */
public class ComponentsEngineTickCallback extends InternalTickCallback {

	MessageQueue messageQueue;

	@Inject
	public void setMessageQueue(MessageQueue messageQueue) {
		this.messageQueue = messageQueue;
	}

	@Override
	public void internalTick(final DynamicsWorld world, final float timeStep) {

		messageQueue.enqueue(new Message("physicsUpdate", new PropertiesMapBuilder() {
			{
				property("world", world);
				property("timeStep", timeStep);
			}
		}.build()));

	}
}