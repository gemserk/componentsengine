package com.gemserk.jbullet.utils;

import javax.vecmath.Matrix3f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.StaticPlaneShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;

/**
 * Utilities for jbullet hardcoded for 2d games. TODO: make it less specific.
 */
public class JBulletUtils {

	private static final Transform transform = new Transform();

	private static final Vector3f vector3f = new Vector3f();

	private static final Matrix4f matrix4f = new Matrix4f();

	private static final Matrix3f matrix3f = new Matrix3f();

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

	public CollisionShape staticPlane(float a, float b, float c, float d) {
		return new StaticPlaneShape(new Vector3f(a, b, c), d);
	}

	public static RigidBody setPosition(RigidBody rigidBody, Vector3f position) {

		rigidBody.getWorldTransform(transform);
		transform.origin.set(position);
		rigidBody.setWorldTransform(transform);

		if (!rigidBody.isStaticObject()) {
			rigidBody.getMotionState().getWorldTransform(transform);
			transform.origin.set(position);
			rigidBody.getMotionState().setWorldTransform(transform);
		}

		return rigidBody;
	}

	public static RigidBody setPosition(RigidBody rigidBody, Vector2f position) {
		vector3f.set(position.x, position.y, 0f);
		return setPosition(rigidBody, vector3f);
	}

	public static Vector3f getPosition(RigidBody rigidBody) {
		if (!rigidBody.isStaticObject())
			rigidBody.getMotionState().getWorldTransform(transform);
		else
			rigidBody.getWorldTransform(transform);
		vector3f.set(transform.origin);
		return vector3f;
	}

	public static void setDirection(RigidBody rigidBody, float angle) {
		rigidBody.getWorldTransform(transform);
		transform.basis.rotZ(angle);
		rigidBody.setWorldTransform(transform);

		if (!rigidBody.isStaticObject()) {
			rigidBody.getMotionState().getWorldTransform(transform);
			transform.basis.rotZ(angle);
			rigidBody.getMotionState().setWorldTransform(transform);
		}
	}

	public static Vector3f getDirection(RigidBody rigidBody) {
		if (!rigidBody.isStaticObject())
			rigidBody.getMotionState().getWorldTransform(transform);
		else
			rigidBody.getWorldTransform(transform);
		transform.getMatrix(matrix4f);
		matrix4f.getRotationScale(matrix3f);
		vector3f.set(1, 0, 0);
		matrix3f.transform(vector3f);
		return vector3f;
	}

}