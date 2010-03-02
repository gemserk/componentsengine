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

	PropertyLocator<Image> imageProperty;
	
	PropertyLocator<Vector2f> positionProperty;
	
	PropertyLocator<Vector2f> directionProperty;
	
	PropertyLocator<Color> renderColorProperty;
	
	PropertyLocator<Vector2f> sizeProperty;

	public ImageRenderableComponent(String id) {
		super(id);
		imageProperty = Properties.property(id, "image");;
		positionProperty = Properties.property(id, "position");
		directionProperty = Properties.property(id, "direction");
		renderColorProperty = Properties.property(id, "color");
		sizeProperty = Properties.property(id, "size");
	}

	public void handleMessage(SlickRenderMessage message) {
		Graphics g = message.getGraphics();

		Vector2f position = positionProperty.getValue(entity);
		Color renderColor = renderColorProperty.getValue(entity, Color.white);
		Vector2f size = sizeProperty.getValue(entity, new Vector2f(1,1));

		g.pushTransform();
		{
			g.translate(position.x, position.y);
			g.scale(size.x, size.y);
			g.rotate(0, 0, (float) directionProperty.getValue(entity).getTheta());

			Image image = imageProperty.getValue(entity);
			g.drawImage(image, -(image.getWidth() / 2), -(image.getHeight() / 2), renderColor);
		}
		g.popTransform();
	}
}
