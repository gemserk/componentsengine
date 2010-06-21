package com.gemserk.componentsengine.commons.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.annotations.EntityProperty;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.Properties;

public class RectangleRendererComponent extends FieldsReflectionComponent {

	@EntityProperty(readOnly=true)
	Vector2f position;
	
	@EntityProperty(readOnly=true, required=false)
	Color lineColor = Color.white;
	
	@EntityProperty(readOnly=true, required=false)
	Color fillColor = new Color(0f, 0f, 0f, 0f);
	
	@EntityProperty(readOnly=true, required=false)
	Integer cornerRadius = null;
	
	@EntityProperty(readOnly=true)
	Rectangle rectangle ;

	public RectangleRendererComponent(String id) {
		super(id);
	}


	@Handles
	public void render(Message message) {
		Graphics g = Properties.getValue(message, "graphics");
		
		g.pushTransform();
		{
			g.translate(position.x, position.y);

			Color color = g.getColor();

			if (cornerRadius != null) {
				g.setColor(fillColor);
				g.fillRoundRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), 5);
				
				g.setColor(lineColor);
				g.drawRoundRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), 5);
			} else {
				g.setColor(fillColor);
				g.fill(rectangle);
				
				g.setColor(lineColor);
				g.draw(rectangle);
			}

			g.setColor(color);

		}
		g.popTransform();
	}

}