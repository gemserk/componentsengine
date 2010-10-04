package com.gemserk.componentsengine.slick.predicates;

import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.properties.PropertyLocator;
import com.google.common.base.Predicate;

public class SlickEntityPredicates {
	private static final PropertyLocator<Vector2f> positionProperty = Properties.property("position");
	
	public static Predicate<Entity> isNear(final Vector2f position, final float distance){
			return new Predicate<Entity>(){
	
				@Override
				public boolean apply(Entity target) {
					
					Vector2f targetPos = positionProperty.getValue(target);
					return position.distance(targetPos) < distance;
					
				}
			};
		}
		
		public static Predicate<Entity> isNear(final Line line, float distance){
			final float distanceSquared = distance*distance;
			return new Predicate<Entity>(){
	
				@Override
				public boolean apply(Entity target) {
					
					Vector2f targetPos = positionProperty.getValue(target);
					return line.distanceSquared(targetPos) < distanceSquared;
					
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

