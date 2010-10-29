package com.gemserk.componentsengine.jbullet.entities;

import javax.vecmath.Matrix3f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import com.bulletphysics.collision.broadphase.CollisionFilterGroups;
import com.bulletphysics.dynamics.RigidBody;
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
import com.gemserk.componentsengine.properties.PropertySetter;
import com.gemserk.componentsengine.templates.EntityBuilder;
import com.gemserk.vecmath.utils.VecmathUtils;
import com.google.inject.Inject;

public class PhysicsRigidBodyEntityBuilder extends EntityBuilder {
	
	@Override
	public void build() {

		tags("physics", "rigidBody");

		Short collisionFilterGroup = (Short) (parameters.get("collisionFilterGroup") != null ? parameters.get("collisionFilterGroup") : CollisionFilterGroups.DEFAULT_FILTER);
		Short collisionFilterMask = (Short) (parameters.get("collisionFilterMask") != null ? parameters.get("collisionFilterMask") : CollisionFilterGroups.ALL_FILTER);

		property("collisionFilterGroup", collisionFilterGroup);
		property("collisionFilterMask", collisionFilterMask);

		final String prefix = (String) (parameters.get("prefix") != null ? parameters.get("prefix") : "physics");

		Vector2f position = (Vector2f) parameters.get("position");
		RigidBody rigidBody = (RigidBody) parameters.get("rigidBody");

		Transform worldTransform = new Transform();
		rigidBody.getWorldTransform(worldTransform);
		worldTransform.origin.set(position.x, position.y, 0f);
		rigidBody.setWorldTransform(worldTransform);

		rigidBody.getMotionState().setWorldTransform(worldTransform);

		// we add the pointer to the entity, so we can access it from the collision object (the rigid body)
		rigidBody.setUserPointer(entity);

		property(prefix + ".rigidBody", rigidBody);
		property(prefix + ".position", new InnerProperty(entity, new PropertyGetter() {

			private final Transform worldTransform = new Transform();
			
			@Override
			public Object get(Entity entity) {
				RigidBody rigidBody = Properties.getValue(entity, prefix + ".rigidBody");


				if (!rigidBody.isStaticObject())
					rigidBody.getMotionState().getWorldTransform(worldTransform);
				else
					rigidBody.getWorldTransform(worldTransform);
				Tuple3f position = worldTransform.origin;

				return new Vector2f(position.x, position.y);
			}
		}, new PropertySetter() {
			
			private final Transform worldTransform = new Transform();
			
			@Override
			public void set(Entity entity, Object value) {
				
				Vector2f position = (Vector2f) value;

				RigidBody rigidBody = Properties.getValue(entity, prefix + ".rigidBody");

				rigidBody.getWorldTransform(worldTransform);
				worldTransform.origin.set(position.x, position.y, 0);
				rigidBody.setWorldTransform(worldTransform);

				if (!rigidBody.isStaticObject()) {
					rigidBody.getMotionState().getWorldTransform(worldTransform);
					worldTransform.origin.set(position.x, position.y, 0);
					rigidBody.getMotionState().setWorldTransform(worldTransform);
				}
				
			}
		}));

		property(prefix + ".direction", new InnerProperty(entity, new PropertyGetter() {

			private final Transform worldTransform = new Transform();

			private final Matrix4f matrix = new Matrix4f();

			private final Matrix3f rotationMatrix = new Matrix3f();

			private final Vector3f directionTmp = new Vector3f();

			private final Vector2f direction = new Vector2f();

			@Override
			public Object get(Entity entity) {
				RigidBody rigidBody = Properties.getValue(entity, prefix + ".rigidBody");
				rigidBody.getMotionState().getWorldTransform(worldTransform);
				worldTransform.getMatrix(matrix);
				matrix.getRotationScale(rotationMatrix);
				directionTmp.set(1, 0, 0);
				rotationMatrix.transform(directionTmp);
				direction.set(directionTmp.x, directionTmp.y);

				return direction;
			}
		}, new PropertySetter() {

			private final Transform worldTransform = new Transform();

			@Override
			public void set(Entity entity, Object value) {

				Vector2f direction = (Vector2f) value;

				RigidBody rigidBody = Properties.getValue(entity, prefix + ".rigidBody");

				rigidBody.getWorldTransform(worldTransform);
				worldTransform.basis.rotZ(VecmathUtils.getThetaInRadians(direction));
				rigidBody.setWorldTransform(worldTransform);

				if (!rigidBody.isStaticObject()) {
					rigidBody.getMotionState().getWorldTransform(worldTransform);
					worldTransform.basis.rotZ(VecmathUtils.getThetaInRadians(direction));
					rigidBody.getMotionState().setWorldTransform(worldTransform);
				}
			}

		}));
		
		property(prefix + ".angle", new InnerProperty(entity, new PropertyGetter() {

			@Override
			public Object get(Entity entity) {
				Vector2f direction = Properties.getValue(entity, prefix + ".direction");
				return VecmathUtils.getThetaInRadians(direction);
			}
			
		}));

		component(new FieldsReflectionComponent("registerBodyComponent") {

			@EntityProperty(readOnly = true)
			private RigidBody rigidBody;

			@EntityProperty(required = false)
			private Boolean inited = false;

			@EntityProperty(readOnly = true)
			Short collisionFilterGroup;

			@EntityProperty(readOnly = true)
			Short collisionFilterMask;

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

				if (inited)
					return;

				Entity addedEntity = (Entity) message.getProperty("entity").get();
				if (entity != addedEntity)
					return;

				messageQueue.enqueue(new Message("addRigidBody", new PropertiesMapBuilder() {
					{
						property("rigidBody", rigidBody);
						property("collisionFilterGroup", collisionFilterGroup);
						property("collisionFilterMask", collisionFilterMask);
					}
				}.build()));

				inited = true;
			}

		}).withProperties(new ComponentProperties() {
			{
				propertyRef("rigidBody", prefix + ".rigidBody");
			}
		});

	}
}