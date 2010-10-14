package com.gemserk.componentsengine.jbullet.entities;

import com.gemserk.componentsengine.jbullet.components.PhysicsUpdateComponent;
import com.gemserk.componentsengine.templates.EntityBuilder;

public class PhysicsWorldEntityBuilder extends EntityBuilder {

	@Override
	public void build() {

		property("world", parameters.get("world"));

		component(new PhysicsUpdateComponent("updateWorld"));
		// component(new PhysicsCollisionDetectionComponent("detectCollisions"));

	}
}