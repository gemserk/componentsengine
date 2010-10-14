package com.gemserk.componentsengine.jbullet.components;

import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.gemserk.componentsengine.components.FieldsReflectionComponent;
import com.gemserk.componentsengine.components.annotations.EntityProperty;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.Properties;

public class PhysicsUpdateComponent extends FieldsReflectionComponent {

	@EntityProperty
	DynamicsWorld world;

	public PhysicsUpdateComponent(String id) {
		super(id);
	}

	@Handles
	public void update(Message message) {
		Integer delta = Properties.getValue(message, "delta");
		float deltaInSeconds = delta / 1000f;
		world.stepSimulation(deltaInSeconds, 7);
	}

	@Handles
	public void addRigidBody(Message message) {
		RigidBody rigidBody = Properties.getValue(message, "rigidBody");
		world.addRigidBody(rigidBody);
	}
}