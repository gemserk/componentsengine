package com.gemserk.componentsengine.jbullet.entities;

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
import com.gemserk.jbullet.utils.JBulletUtils;
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

			private final Vector2f position = new Vector2f();

			@Override
			public Object get(Entity entity) {
				RigidBody rigidBody = Properties.getValue(entity, prefix + ".rigidBody");
				Vector3f position3f = JBulletUtils.getPosition(rigidBody);
				position.set(position3f.x, position3f.y);
				return position;
			}
		}, new PropertySetter() {

			@Override
			public void set(Entity entity, Object value) {
				Vector2f position = (Vector2f) value;
				RigidBody rigidBody = Properties.getValue(entity, prefix + ".rigidBody");
				JBulletUtils.setPosition(rigidBody, position);
			}
		}));

		property(prefix + ".direction", new InnerProperty(entity, new PropertyGetter() {

			private final Vector2f direction = new Vector2f();

			@Override
			public Object get(Entity entity) {
				RigidBody rigidBody = Properties.getValue(entity, prefix + ".rigidBody");
				Vector3f direction3f = JBulletUtils.getDirection(rigidBody);
				direction.set(direction3f.x, direction3f.y);
				return direction;
			}
		}, new PropertySetter() {

			@Override
			public void set(Entity entity, Object value) {
				Vector2f direction = (Vector2f) value;
				RigidBody rigidBody = Properties.getValue(entity, prefix + ".rigidBody");
				JBulletUtils.setDirection(rigidBody, VecmathUtils.getThetaInRadians(direction));
			}

		}));

		property(prefix + ".angle", new InnerProperty(entity, new PropertyGetter() {

			@Override
			public Object get(Entity entity) {
				Vector2f direction = Properties.getValue(entity, prefix + ".direction");
				return VecmathUtils.getThetaInRadians(direction);
			}

		}, new PropertySetter() {

			@Override
			public void set(Entity entity, Object value) {
				Float angle = (Float) value;
				RigidBody rigidBody = Properties.getValue(entity, prefix + ".rigidBody");
				JBulletUtils.setDirection(rigidBody, angle);
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