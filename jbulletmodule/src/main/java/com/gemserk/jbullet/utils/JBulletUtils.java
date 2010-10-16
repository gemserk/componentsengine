package com.gemserk.jbullet.utils;

import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;

/**
 * Utilities for jbullet hardcoded for 2d games. 
 * TODO: make it less specific.
 */
public class JBulletUtils {

	public RigidBody rigidBody(CollisionShape collisionShape, float mass, float friction, float linearDamping, float angularDamping, float restitution) {
		Transform transform = new Transform();
		transform.setIdentity();
		transform.origin.set(0f, 0f, 0f);

		DefaultMotionState motionState = new DefaultMotionState(transform);
		Vector3f localInertia = new Vector3f();

		collisionShape.calculateLocalInertia(mass, localInertia);
		RigidBodyConstructionInfo rigidBodyConstructionInfo = new RigidBodyConstructionInfo(mass, motionState, collisionShape, localInertia);
		RigidBody rigidBody = new RigidBody(rigidBodyConstructionInfo);

		rigidBody.setFriction(friction);
		rigidBody.setDamping(linearDamping, angularDamping);
		rigidBody.setLinearFactor(new Vector3f(1f, 1f, 0f));
		rigidBody.setAngularFactor(new Vector3f(0f, 0f, 1f));
		rigidBody.setRestitution(restitution);

		return rigidBody;
	}

}