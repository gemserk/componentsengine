package com.gemserk.componentsengine.jbullet.components;

import com.bulletphysics.collision.broadphase.Dispatcher;
import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.dispatch.CollisionWorld;
import com.bulletphysics.collision.narrowphase.ManifoldPoint;
import com.bulletphysics.collision.narrowphase.PersistentManifold;
import com.gemserk.componentsengine.components.FieldsReflectionComponent;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.properties.PropertiesMapBuilder;
import com.google.inject.Inject;

public class PhysicsCollisionDetectionComponent extends FieldsReflectionComponent {

	MessageQueue messageQueue;

	@Inject
	public void setMessageQueue(MessageQueue messageQueue) {
		this.messageQueue = messageQueue;
	}
	
	public PhysicsCollisionDetectionComponent(String id) {
		super(id);
	}

	@Handles
	public void physicsUpdate(Message message) {
		CollisionWorld world = Properties.getValue(message, "world");
		Float timeStep = Properties.getValue(message, "timeStep");

		Dispatcher dispatcher = world.getDispatcher();
		int numManifolds = dispatcher.getNumManifolds();
		for (int i = 0; i < numManifolds; i++) {
			PersistentManifold contactManifold = dispatcher.getManifoldByIndexInternal(i);
			final CollisionObject body0 = (CollisionObject) contactManifold.getBody0();
			final CollisionObject body1 = (CollisionObject) contactManifold.getBody1();

			int numContacts = contactManifold.getNumContacts();
			for (int j = 0; j < numContacts; j++) {
				final ManifoldPoint contactPoint = contactManifold.getContactPoint(j);
				messageQueue.enqueue(new Message("physicsContactProcessed", new PropertiesMapBuilder() {
					{
						property("cp", contactPoint);
						property("body0", body0);
						property("body1", body1);
					}
				}.build()));
			}
		}
	}
}