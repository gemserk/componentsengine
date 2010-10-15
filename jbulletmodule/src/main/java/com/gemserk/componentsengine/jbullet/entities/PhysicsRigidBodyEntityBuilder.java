package com.gemserk.componentsengine.jbullet.entities;
import javax.vecmath.Matrix3f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;
import com.gemserk.componentsengine.components.FieldsReflectionComponent;
import com.gemserk.componentsengine.components.annotations.EntityProperty;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.properties.InnerProperty;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.properties.PropertiesMapBuilder;
import com.gemserk.componentsengine.properties.PropertyGetter;
import com.gemserk.componentsengine.templates.EntityBuilder;
import com.gemserk.vecmath.utils.VecmathUtils;
import com.google.inject.Inject;

public class PhysicsRigidBodyEntityBuilder extends EntityBuilder {

	@Override
	public void build() {

		tags("physics", "rigidBody");

		Vector2f position = (Vector2f) parameters.get("position");
		Float mass = (Float) (parameters.get("mass") != null ? parameters.get("mass") : 1.0f);
		CollisionShape collisionShape = (CollisionShape) parameters.get("collisionShape");
		Float friction = (Float) (parameters.get("friction") != null ? parameters.get("friction") : 1.0f);
		
		Float linearDamping = (Float) (parameters.get("linearDamping") != null ? parameters.get("linearDamping") : 0.8f);
		Float angularDamping = (Float) (parameters.get("angularDamping") != null ? parameters.get("angularDamping") : 0.95f);
		Float restitution = (Float) (parameters.get("restitution") != null ? parameters.get("restitution") : 0f);

		Transform transform = new Transform();
		transform.setIdentity();
		transform.origin.set(position.x, position.y, 0);

		DefaultMotionState motionState = new DefaultMotionState(transform);
		Vector3f localInertia = new Vector3f();

		collisionShape.calculateLocalInertia(mass, localInertia);
		RigidBodyConstructionInfo rigidBodyConstructionInfo = new RigidBodyConstructionInfo(mass, motionState, collisionShape, localInertia);
		final RigidBody rigidBody = new RigidBody(rigidBodyConstructionInfo);

		rigidBody.setFriction(friction);
		rigidBody.setDamping(linearDamping, angularDamping);
		rigidBody.setLinearFactor(new Vector3f(1f, 1f, 0f));
		rigidBody.setAngularFactor(new Vector3f(0f, 0f, 1f));
		rigidBody.setUserPointer(entity);
		rigidBody.setRestitution(restitution);

		property("physics.rigidBody", rigidBody);
		property("physics.position", new InnerProperty(entity, new PropertyGetter() {

			@Override
			public Object get(Entity entity) {
				RigidBody rigidBody = Properties.getValue(entity, "physics.rigidBody");

				Transform worldTransform = new Transform();
				rigidBody.getMotionState().getWorldTransform(worldTransform);
				Tuple3f position = worldTransform.origin;

				return new Vector2f(position.x, position.y);
			}
		}));
		property("physics.direction", new InnerProperty(entity, new PropertyGetter() {

			@Override
			public Object get(Entity entity) {
				RigidBody rigidBody = Properties.getValue(entity, "physics.rigidBody");

				Transform worldTransform = new Transform();
				rigidBody.getMotionState().getWorldTransform(worldTransform);

				Matrix4f matrix = new Matrix4f();
				worldTransform.getMatrix(matrix);

				Matrix3f rotationMatrix = new Matrix3f();
				matrix.getRotationScale(rotationMatrix);

				Vector3f direction = new Vector3f(1, 0, 0);
				rotationMatrix.transform(direction);

				return new Vector2f(direction.x, direction.y);
			}
		}));
		property("physics.angle", new InnerProperty(entity, new PropertyGetter() {

			@Override
			public Object get(Entity entity) {
				Vector2f direction = Properties.getValue(entity, "physics.direction");
				return VecmathUtils.getThetaInRadians(direction);
			}
		}));
		
		component(new FieldsReflectionComponent("registerBodyComponent") {
			
			@EntityProperty(readOnly=true)
			private RigidBody rigidBody;
			
			private MessageQueue messageQueue;
			
			public void setRigidBody(RigidBody rigidBody) {
				this.rigidBody = rigidBody;
			}
			
			@Inject 
			public void setMessageQueue(MessageQueue messageQueue) {
				this.messageQueue = messageQueue;
			}
			
			@Handles
			public void entityAdded(Message message) {
				
				Entity addedEntity = (Entity) message.getProperty("entity").get(); 
				if(entity!=addedEntity)
					return;
				
				
				messageQueue.enqueue(new Message("addRigidBody", new PropertiesMapBuilder(){{
					property("rigidBody", rigidBody);
				}}.build())) ;
				
			}
			
		}).withProperties(new ComponentProperties() {
			{
				propertyRef("rigidBody", "physics.rigidBody");
			}
		});

	}
}