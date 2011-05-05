package com.gemserk.componentsengine.modules;

import com.gemserk.componentsengine.components.ChildrenManagementComponent;
import com.gemserk.componentsengine.components.DelayedMessagesComponent;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.entities.EntityManager;
import com.gemserk.componentsengine.entities.Root;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;

public class InitEntityManager {
	@Inject
	Injector injector;

	public void config() {
		final Entity rootEntity = injector.getInstance(Key.get(Entity.class, Root.class));
		ChildrenManagementComponent childrenManagementComponent = new ChildrenManagementComponent("childrenManagementComponent");
		injector.injectMembers(childrenManagementComponent);
		rootEntity.addComponent(childrenManagementComponent);
		DelayedMessagesComponent delayedMessagesComponent = new DelayedMessagesComponent("delayedMessagesComponent");
		injector.injectMembers(delayedMessagesComponent);
		rootEntity.addComponent(delayedMessagesComponent);

		EntityManager entityManager = injector.getInstance(EntityManager.class);
		entityManager.addEntity(rootEntity, null);
	}
}