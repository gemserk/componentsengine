package com.gemserk.componentsengine.jbullet.components;

import com.bulletphysics.collision.broadphase.Dispatcher;
import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.dispatch.CollisionWorld;
import com.bulletphysics.collision.narrowphase.ManifoldPoint;
import com.bulletphysics.collision.narrowphase.PersistentManifold;
import com.gemserk.componentsengine.components.FieldsReflectionComponent;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.Properties;

public class PhysicsCollisionDetectionComponent extends FieldsReflectionComponent {

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
			CollisionObject body0 = (CollisionObject) contactManifold.getBody0();
			CollisionObject body1 = (CollisionObject) contactManifold.getBody1();

			int numContacts = contactManifold.getNumContacts();
			for (int j = 0; j < numContacts; j++) {
				ManifoldPoint contactPoint = contactManifold.getContactPoint(j);
			}
		}
	}
}