package com.gemserk.componentsengine.commons.components;

import java.util.Collection;

import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.components.ReflectionComponent;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.entities.Root;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.predicates.EntityPredicates;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.properties.PropertyLocator;
import com.google.common.base.Predicates;
import com.google.inject.Inject;

public class SelectTargetWithinRangeComponent extends ReflectionComponent {

	@Inject	@Root 
	Entity  rootEntity;

	private PropertyLocator<String> targetTagProperty;

	private PropertyLocator<Vector2f> positionProperty;

	private PropertyLocator<Float> radiusProperty;

	private PropertyLocator<Entity> targetEntityProperty;

	public SelectTargetWithinRangeComponent(String name) {
		super(name);
		targetTagProperty = Properties.property(id, "targetTag");
		positionProperty = Properties.property(id, "position");
		radiusProperty = Properties.property(id, "radius");
		targetEntityProperty = Properties.property(id, "targetEntity");
	}

	@Handles
	public void update(Message message) {
		String targetTag = targetTagProperty.getValue(entity);

		Vector2f position = positionProperty.getValue(entity);

		float radius = radiusProperty.getValue(entity);

		Collection<Entity> targetEntities = rootEntity.getEntities(Predicates.and(EntityPredicates.withAllTags(targetTag), EntityPredicates.isNear(position, radius)));

		if (targetEntities.size() == 0) {
			targetEntityProperty.setValue(entity, null);
			return;
		}

		PropertyLocator<Vector2f> targetPositionProperty = Properties.property("position");
		Entity selectedEntity = targetEntities.iterator().next();
		/*Vector2f selectedEntityPosition = targetPositionProperty.getValue(selectedEntity);
		for (Entity candidate : targetEntities) {
			Vector2f positionCandidate = targetPositionProperty.getValue(candidate);
			if(positionCandidate.distance(position) < selectedEntityPosition.distance(position)){
				selectedEntity = candidate;
				selectedEntityPosition = targetPositionProperty.getValue(candidate);
			}
		}*/
		
		
		targetEntityProperty.setValue(entity, selectedEntity);
	}
}