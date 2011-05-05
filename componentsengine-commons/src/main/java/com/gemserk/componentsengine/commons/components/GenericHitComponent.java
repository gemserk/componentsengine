package com.gemserk.componentsengine.commons.components;

import java.util.*;

import com.gemserk.componentsengine.components.ReflectionComponent;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.entities.*;
import com.gemserk.componentsengine.messages.*;
import com.gemserk.componentsengine.predicates.EntityPredicates;
import com.gemserk.componentsengine.properties.*;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.triggers.Trigger;
import com.google.common.base.*;
import com.google.inject.Inject;

public class GenericHitComponent extends ReflectionComponent {

	private PropertyLocator<Trigger> messageBuilderProperty;

	private PropertyLocator<String> targetTagProperty;

	private PropertyLocator<Predicate<Entity>> predicateProperty;
	
	@Inject	@Root 
	Entity  rootEntity;
	
	@Inject
	MessageQueue messageQueue;

	public GenericHitComponent(String id) {
		super(id);

		messageBuilderProperty = Properties.property(id, "trigger");
		targetTagProperty = Properties.property(id, "targetTag");
		predicateProperty = Properties.property(id,"predicate");

	}

	@Handles
	public void update(Message message) {


		String targetTags = targetTagProperty.getValue(entity);

		Predicate<Entity> areaPredicate = predicateProperty.getValue(entity);
		final Collection<Entity> candidates = rootEntity.getEntities(Predicates.and(EntityPredicates.withAllTags(targetTags), areaPredicate));

		if (candidates.size() == 0)
			return;

		final int delta = (Integer) Properties.getValue(message, "delta");
		messageBuilderProperty.getValue(entity).trigger(new HashMap<String, Object>() {
			{
				put("source", entity);
				put("targets", new ArrayList<Entity>(candidates));
				put("delta",delta);
			}
		});
	}

}