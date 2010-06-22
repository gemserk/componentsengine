package com.gemserk.componentsengine.commons.components;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.annotations.EntityProperty;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.messages.Message;

public class WorldBoundsComponent extends FieldsReflectionComponent {

	@EntityProperty(readOnly=true)
	private Rectangle bounds;

	@EntityProperty
	private Vector2f position;
	
	public WorldBoundsComponent(String id) {
		super(id);
	}
	
	@Handles
	public void update(Message message) {
		
		float height = bounds.getMaxY();
		float width = bounds.getMaxX();
		
		float minX = bounds.getMinX();
		float minY = bounds.getMinY();
		
		float x = position.x;
		float y = position.y;

		if (x < minX)
			x = minX;
		if (x > width)
			x = width;
		if (y < minY)
			y = minY;
		if (y > height)
			y = height;

		position = new Vector2f(x,y);
	}
}
