package com.gemserk.componentsengine.commons.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.components.ReflectionComponent;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.messages.SlickRenderMessage;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.properties.PropertyLocator;

public class ImageRenderableComponent extends ReflectionComponent {

	PropertyLocator<Image> imageLocator = Properties.property("image");;

	PropertyLocator<Vector2f> positionProperty = Properties.property("position");

	PropertyLocator<Vector2f> directionProperty = Properties.property("direction");

	PropertyLocator<Color> renderColorProperty = Properties.property("color");

	public ImageRenderableComponent(String id) {
		super(id);

	}

	public void handleMessage(SlickRenderMessage message) {
		Graphics g = message.getGraphics();
		Entity entity = message.getEntity();

		Vector2f posEntity = positionProperty.getValue(entity);
		Color renderColor = renderColorProperty.getValue(entity, Color.white);

		g.pushTransform();
		{
			g.translate(posEntity.x, posEntity.y);
			g.rotate(0, 0, (float) directionProperty.getValue(entity).getTheta());

			Image image = imageLocator.get(entity).get();
			g.drawImage(image, -(image.getWidth() / 2), -(image.getHeight() / 2), renderColor);
		}
		g.popTransform();
	}
}
