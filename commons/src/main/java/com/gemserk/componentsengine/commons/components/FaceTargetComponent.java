package com.gemserk.componentsengine.commons.components;

import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.components.annotations.EntityProperty;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.utils.AngleUtils;

public class FaceTargetComponent extends FieldsReflectionComponent {

	@EntityProperty(readOnly=true)
	Vector2f position;
	
	@EntityProperty
	Vector2f direction;

	@EntityProperty
	Float turnRate = 0.01f;

	@EntityProperty
	Entity targetEntity;
	
	private final AngleUtils angleUtils = new AngleUtils();
	
	public FaceTargetComponent(String id) {
		super(id);
	}

	@Handles
	public void update(Message message) {

		if (targetEntity == null)
			return;

		Vector2f targetPosition = (Vector2f) Properties.property("position").getValue(targetEntity);
		Vector2f desiredDirection = targetPosition.copy().sub(position).normalise();
		
		int delta = (Integer) Properties.getValue(message, "delta");

		turnToAngle(delta, direction.getTheta(), desiredDirection.getTheta());
		
	}

	protected void turnToAngle(int delta, double currentAngle, double desiredAngle) {
		direction.setTheta(calculateNextAngle(delta, currentAngle, desiredAngle));
	}

	double calculateNextAngle(int delta, double currentAngle, double desiredAngle) {
		return angleUtils.calculateTruncatedNextAngle(turnRate * delta, currentAngle, desiredAngle);
	}
	

}