package com.gemserk.componentsengine.jbullet;
import com.bulletphysics.collision.broadphase.BroadphaseProxy;
import com.bulletphysics.collision.broadphase.OverlapFilterCallback;
import com.bulletphysics.collision.dispatch.CollisionObject;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.properties.Properties;

/**
 * Uses the CollisionObject pointer as an Entity and decide whether the entities collides or not using a property collisionFilterMask from the entity.
 */
public class ComponentsEngineOverlapFilterCallback extends OverlapFilterCallback {

	@Override
	public boolean needBroadphaseCollision(BroadphaseProxy proxy0, BroadphaseProxy proxy1) {

		boolean collides = (proxy0.collisionFilterGroup & proxy1.collisionFilterMask) != 0;
		collides = collides && ((proxy1.collisionFilterGroup & proxy0.collisionFilterMask) != 0);

		CollisionObject body0 = (CollisionObject) proxy0.clientObject;
		CollisionObject body1 = (CollisionObject) proxy1.clientObject;

		if (body0 == null)
			return collides;

		if (body1 == null)
			return collides;

		Entity entity0 = (Entity) body0.getUserPointer();
		Entity entity1 = (Entity) body1.getUserPointer();

		if (entity0 == null)
			return collides;

		if (entity1 == null)
			return collides;

		Integer filterMask0 = Properties.getValue(entity0, "collisionFilterMask");
		Integer filterGroup0 = Properties.getValue(entity0, "collisionFilterGroup");

		if (filterMask0 == null || filterGroup0 == null)
			return collides;

		Integer filterMask1 = Properties.getValue(entity1, "collisionFilterMask");
		Integer filterGroup1 = Properties.getValue(entity1, "collisionFilterGroup");

		if (filterMask1 == null || filterGroup1 == null)
			return collides;

		collides = collides && ((filterMask0 & filterGroup1) != 0);
		collides = collides && ((filterMask1 & filterGroup0) != 0);

		return collides;
	}

}