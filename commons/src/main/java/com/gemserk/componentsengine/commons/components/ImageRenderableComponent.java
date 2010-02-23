package com.gemserk.componentsengine.commons.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.components.ReflectionComponent;
import com.gemserk.componentsengine.messages.SlickRenderMessage;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.properties.PropertyLocator;

public class ImageRenderableComponent extends ReflectionComponent {

	private PropertyLocator<Image> imageLocator;
	private PropertyLocator<Vector2f> positionProperty;
	private PropertyLocator<Vector2f> directionProperty;
	private PropertyLocator<Color> renderColorProperty;

	public ImageRenderableComponent(String id) {
		super(id);
		imageLocator = Properties.property(id, "image");;
		positionProperty = Properties.property(id, "position");
		directionProperty = Properties.property(id, "direction");
		renderColorProperty = Properties.property(id, "color");
	}

	public void handleMessage(SlickRenderMessage message) {
		Graphics g = message.getGraphics();

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
