package com.gemserk.componentsengine.predicates;

import java.util.Set;

import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.properties.PropertyLocator;
import com.google.common.base.Predicate;

public class EntityPredicates {
	
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
}
