package com.gemserk.componentsengine.commons.components;

import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.UpdateMessage;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.properties.PropertyLocator;

public class SuperMovementComponent extends Component {

	PropertyLocator<Vector2f> positionProperty = Properties.property("movement.position");

	PropertyLocator<Vector2f> velocityProperty = Properties.property("movement.velocity");

	PropertyLocator<Float> maxVelocityProperty = Properties.property("movement.maxVelocity");

	PropertyLocator<Vector2f> forceProperty = Properties.property("movement.force");

	PropertyLocator<Float> frictionFactorProperty = Properties.property("movement.frictionFactor");

	public SuperMovementComponent(String id) {
		super(id);
	}

	private void update(final Entity entity, int delta) {
		Vector2f position = this.positionProperty.getValue(entity);
		Vector2f velocity = this.velocityProperty.getValue(entity);
		Vector2f force = this.forceProperty.getValue(entity);
		float maxVelocity = this.maxVelocityProperty.getValue(entity);

		if (!entity.hasTag("nofriction")) {
			float frictionFactor = frictionFactorProperty.getValue(entity, 0.0005f);
			force = force.copy().add(velocity.copy().negateLocal().scale(frictionFactor));
		}

		Vector2f deltaV = force.copy().scale(delta);
		Vector2f newVelocity = velocity.copy().add(deltaV);

		newVelocity = truncate(newVelocity, maxVelocity);

		Vector2f newPosition = position.copy().add(newVelocity.copy().scale(delta));

		this.velocityProperty.setValue(entity, newVelocity);
		this.positionProperty.setValue(entity, newPosition);

		// reseteamos la fuerza iteracion tras iteracion.
		this.forceProperty.setValue(entity, new Vector2f());
	}

	private Vector2f truncate(Vector2f newVelocity, float maxVelocity) {
		float length = newVelocity.length();

		if (length > maxVelocity)
			return newVelocity.copy().scale(maxVelocity / length);

		return newVelocity.copy();
	}

	@Override
	public void handleMessage(Message message) {
		if (message instanceof UpdateMessage) {
			UpdateMessage update = (UpdateMessage) message;
			this.update(message.getEntity(), update.getDelta());
		}
	}

	@Override
	public void onAdd(Entity entity) {
		velocityProperty.setValue(entity, new Vector2f());
		forceProperty.setValue(entity, new Vector2f());
	}

}