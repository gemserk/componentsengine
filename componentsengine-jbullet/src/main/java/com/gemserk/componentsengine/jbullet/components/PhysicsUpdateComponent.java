package com.gemserk.componentsengine.jbullet.components;

import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.gemserk.componentsengine.components.FieldsReflectionComponent;
import com.gemserk.componentsengine.components.annotations.EntityProperty;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.Properties;

public class PhysicsUpdateComponent extends FieldsReflectionComponent {

	@EntityProperty
	DiscreteDynamicsWorld world;

	public PhysicsUpdateComponent(String id) {
		super(id);
	}

	@Handles
	public void update(Message message) {
		Integer delta = Properties.getValue(message, "delta");
		float deltaInSeconds = delta / 1000f;
		world.stepSimulation(deltaInSeconds, 7);
	}
	
	// TODO: separate in different components

	@Handles
	public void addRigidBody(Message message) {
		RigidBody rigidBody = Properties.getValue(message, "rigidBody");
		
		Short collisionFilterGroup = Properties.getValue(message, "collisionFilterGroup");
		Short collisionFilterMask = Properties.getValue(message, "collisionFilterMask");
		
		world.addRigidBody(rigidBody, collisionFilterGroup, collisionFilterMask);
	}
	
	@Handles
	public void removeRigidBody(Message message) {
		RigidBody rigidBody = Properties.getValue(message, "rigidBody");
		world.removeRigidBody(rigidBody);
	}
}