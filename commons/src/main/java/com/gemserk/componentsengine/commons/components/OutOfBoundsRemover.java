package com.gemserk.componentsengine.commons.components;

import java.util.Collection;

import org.newdawn.slick.geom.Rectangle;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.entities.*;
import com.gemserk.componentsengine.messages.*;
import com.gemserk.componentsengine.predicates.EntityPredicates;
import com.gemserk.componentsengine.properties.*;
import com.google.common.base.Predicates;
import com.google.inject.Inject;

public class OutOfBoundsRemover extends Component {

	private PropertyLocator<Rectangle> boundsProperty;

	private PropertyLocator<String[]> tagsProperties;

	@Inject	@Root 
	Entity  rootEntity;

	@Inject
	MessageQueue messageQueue;

	public OutOfBoundsRemover(String id) {
		super(id);
		boundsProperty = Properties.property(id, "bounds");
		tagsProperties = Properties.property(id, "tags");
	}

	@Override
	public void handleMessage(Message message) {
		if (message instanceof UpdateMessage) {

			
			String[] tags = tagsProperties.getValue(entity);

			Rectangle worldBounds = boundsProperty.getValue(entity);

			Collection<Entity> entitiesToRemove = rootEntity.getEntities(Predicates.and(EntityPredicates.withAnyTag(tags), Predicates.not(EntityPredicates.isIn(worldBounds))));

			for (Entity entityToRemove : entitiesToRemove) {
				messageQueue.enqueueDelay(ChildrenManagementMessageFactory.removeEntity(entityToRemove));
			}

		}
	}

}
