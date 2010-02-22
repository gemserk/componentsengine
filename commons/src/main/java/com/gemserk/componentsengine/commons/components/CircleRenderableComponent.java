package com.gemserk.componentsengine.commons.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.components.ReflectionComponent;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.messages.SlickRenderMessage;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.properties.PropertyLocator;

public class CircleRenderableComponent extends ReflectionComponent {



	private PropertyLocator<Vector2f> positionProperty;
	private PropertyLocator<Float> radiusProperty;
	private PropertyLocator<Color> fillColorProperty;
	private PropertyLocator<Color> lineColorProperty;

	public CircleRenderableComponent(String id) {
		super(id);
		positionProperty = Properties.property(id, "position");
		radiusProperty = Properties.property(id, "radius");
		lineColorProperty = Properties.property(id, "lineColor");
		fillColorProperty = Properties.property(id, "fillColor");
	}

	public void handleMessage(SlickRenderMessage message) {
		Graphics g = message.getGraphics();
		Vector2f position = positionProperty.getValue(entity);
		float radius = radiusProperty.getValue(entity);

		Color lineColor = lineColorProperty.getValue(entity, Color.white);
		Color fillColor = fillColorProperty.getValue(entity, null);

		g.pushTransform();
		{
			g.translate(position.getX(), position.getY());

			if (fillColor != null) {
				g.setColor(fillColor);
				g.fillOval(-radius, -radius, 2 * radius, 2 * radius);
			}

			if (lineColor.a > 0.0f) {
				g.setColor(lineColor);
				float lineWidth = g.getLineWidth();
				g.setLineWidth(2.0f);
				g.drawOval(-radius, -radius, 2 * radius, 2 * radius);
				g.setLineWidth(lineWidth);
			}
		}
		g.popTransform();
	}

}
