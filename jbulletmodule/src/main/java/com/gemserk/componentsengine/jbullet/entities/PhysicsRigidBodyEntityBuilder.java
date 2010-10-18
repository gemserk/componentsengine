package com.gemserk.componentsengine.jbullet.entities;

import javax.vecmath.Matrix3f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

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
import com.gemserk.componentsengine.templates.EntityBuilder;
import com.gemserk.vecmath.utils.VecmathUtils;
import com.google.inject.Inject;

public class PhysicsRigidBodyEntityBuilder extends EntityBuilder {

	@Override
	public void build() {

		tags("physics", "rigidBody");

		final String prefix = (String) (parameters.get("prefix") != null ? parameters.get("prefix") : "physics");

		Vector2f position = (Vector2f) parameters.get("position");
		RigidBody rigidBody = (RigidBody) parameters.get("rigidBody");

		Transform worldTransform = new Transform();
		rigidBody.getWorldTransform(worldTransform);
		worldTransform.origin.set(position.x, position.y, 0f);
		rigidBody.setWorldTransform(worldTransform);
		
		// we add the pointer to the entity, so we can access it from the collision object (the rigid body)
		rigidBody.setUserPointer(entity);

		property(prefix + ".rigidBody", rigidBody);
		property(prefix + ".position", new InnerProperty(entity, new PropertyGetter() {

			@Override
			public Object get(Entity entity) {
				RigidBody rigidBody = Properties.getValue(entity, prefix + ".rigidBody");

				Transform worldTransform = new Transform();
				rigidBody.getMotionState().getWorldTransform(worldTransform);
				Tuple3f position = worldTransform.origin;

				return new Vector2f(position.x, position.y);
			}
		}));
		property(prefix + ".direction", new InnerProperty(entity, new PropertyGetter() {

			@Override
			public Object get(Entity entity) {
				RigidBody rigidBody = Properties.getValue(entity, prefix + ".rigidBody");

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
			
			@EntityProperty(required=false)
			private Boolean inited = false;

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