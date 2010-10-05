package com.gemserk.componentsengine.commons.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.components.FieldsReflectionComponent;
import com.gemserk.componentsengine.components.annotations.EntityProperty;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.render.RenderQueue;
import com.gemserk.componentsengine.slick.render.SlickCallableRenderObject;

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

	@EntityProperty(readOnly = true, required = false)
	int layer = 0;
	
	public RectangleRendererComponent(String id) {
		super(id);
	}

	@Handles
	public void render(Message message) {
		RenderQueue renderQueue = Properties.getValue(message, "renderer");
		renderQueue.enqueue(new SlickCallableRenderObject(layer) {

			@Override
			public void execute(Graphics g) {
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
		});
	}

}