package com.gemserk.componentsengine.commons.components;

import java.util.Collection;

import org.newdawn.slick.geom.Rectangle;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.components.ReflectionComponent;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.entities.*;
import com.gemserk.componentsengine.messages.*;
import com.gemserk.componentsengine.predicates.EntityPredicates;
import com.gemserk.componentsengine.properties.*;
import com.gemserk.componentsengine.slick.predicates.SlickEntityPredicates;
import com.google.common.base.Predicates;
import com.google.inject.Inject;

public class OutOfBoundsRemover extends ReflectionComponent {

	private PropertyLocator<Rectangle> boundsProperty;

	private PropertyLocator<String[]> tagsProperties;

	@Inject
	@Root
	Entity rootEntity;

	@Inject
	MessageQueue messageQueue;
	
	@Inject ChildrenManagementMessageFactory childrenManagementMessageFactory;

	public OutOfBoundsRemover(String id) {
		super(id);
		boundsProperty = Properties.property(id, "bounds");
		tagsProperties = Properties.property(id, "tags");
	}

	@Handles
	public void update(Message message) {

		String[] tags = tagsProperties.getValue(entity);

		Rectangle worldBounds = boundsProperty.getValue(entity);

		Collection<Entity> entitiesToRemove = rootEntity.getEntities(Predicates.and(EntityPredicates.withAnyTag(tags), Predicates.not(SlickEntityPredicates.isIn(worldBounds))));

		for (Entity entityToRemove : entitiesToRemove) {
			messageQueue.enqueueDelay(childrenManagementMessageFactory.removeEntity(entityToRemove));
		}

	}

}
