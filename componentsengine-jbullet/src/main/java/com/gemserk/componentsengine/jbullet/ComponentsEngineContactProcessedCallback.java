package com.gemserk.componentsengine.jbullet;
import com.bulletphysics.ContactProcessedCallback;
import com.bulletphysics.collision.narrowphase.ManifoldPoint;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.properties.PropertiesMapBuilder;
import com.google.inject.Inject;

/**
 * Enqueues a message physicsContactProcessed to components engine whenever contact processed callback is called.
 */
public class ComponentsEngineContactProcessedCallback extends ContactProcessedCallback {

	MessageQueue messageQueue;

	@Inject
	public void setMessageQueue(MessageQueue messageQueue) {
		this.messageQueue = messageQueue;
	}

	@Override
	public boolean contactProcessed(final ManifoldPoint cp, final Object body0, final Object body1) {
		messageQueue.enqueue(new Message("physicsContactProcessed", new PropertiesMapBuilder() {
			{
				property("cp", cp);
				property("body0", body0);
				property("body1", body1);
			}
		}.build()));
		return false;
	}
}