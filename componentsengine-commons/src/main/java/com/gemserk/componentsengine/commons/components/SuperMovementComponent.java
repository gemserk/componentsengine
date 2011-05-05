package com.gemserk.componentsengine.commons.components;

import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.components.ReflectionComponent;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.properties.PropertyLocator;

public class SuperMovementComponent extends ReflectionComponent {



	private PropertyLocator<Vector2f> positionProperty;
	private PropertyLocator<Vector2f> velocityProperty;
	private PropertyLocator<Float> maxVelocityProperty;
	private PropertyLocator<Vector2f> forceProperty;
	private PropertyLocator<Float> frictionFactorProperty;
	private PropertyLocator<Integer> deltaProperty;
	

	public SuperMovementComponent(String id) {
		super(id);
		positionProperty = Properties.property(id, "position");

		velocityProperty = Properties.property(id, "velocity");

		maxVelocityProperty = Properties.property(id, "maxVelocity");

		forceProperty = Properties.property(id, "force");

		frictionFactorProperty = Properties.property(id, "frictionFactor");
		
		deltaProperty = Properties.property("delta");
	}

	@Handles
	public void update(Message message) {
		int delta = deltaProperty.getValue(message);
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
	public void onAdd(Entity entity) {
		super.onAdd(entity);
		velocityProperty.setValue(entity,velocityProperty.getValue(entity,new Vector2f()));
		forceProperty.setValue(entity, new Vector2f());
	}

}
