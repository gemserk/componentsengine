package com.gemserk.componentsengine.predicates;

import java.util.Set;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.properties.PropertyLocator;
import com.google.common.base.Predicate;

public class EntityPredicates {
	private static final PropertyLocator<Vector2f> positionProperty = Properties.property("position");
	
	public static Predicate<Entity> withAllTags(final String... tags) {
		return new Predicate<Entity>() {

			@Override
			public boolean apply(Entity entity) {
				for (String tag : tags) {
					Set<String> tagsEntity = entity.getTags();
					if (!tagsEntity.contains(tag))
						return false;
				}
				return true;
			}
		};
	}
	
	public static Predicate<Entity> withAnyTag(final String... tags) {
		return new Predicate<Entity>() {

			@Override
			public boolean apply(Entity entity) {
				for (String tag : tags) {
					Set<String> tagsEntity = entity.getTags();
					if (tagsEntity.contains(tag))
						return true;
				}
				return false;
			}
		};
	}
	
	public static Predicate<Entity> isNear(final Vector2f position, final float distance){
		return new Predicate<Entity>(){

			@Override
			public boolean apply(Entity target) {
				
				Vector2f targetPos = positionProperty.getValue(target);
				return position.distance(targetPos) < distance;
				
			}
		};
	}
	
	public static Predicate<Entity> isIn(final Rectangle rectangle){
		return new Predicate<Entity>(){

			@Override
			public boolean apply(Entity target) {				
				Vector2f position = positionProperty.getValue(target);
				return rectangle.contains(position.x, position.y);				
			}
			
		};
	}
}
