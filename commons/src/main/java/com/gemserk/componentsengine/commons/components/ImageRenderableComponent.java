package com.gemserk.componentsengine.commons.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.components.ReflectionComponent;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.properties.PropertyLocator;
import com.gemserk.componentsengine.render.Renderer;
import com.gemserk.componentsengine.render.SlickCallableRenderObject;

public class ImageRenderableComponent extends ReflectionComponent {

	PropertyLocator<Image> imageProperty;

	PropertyLocator<Vector2f> positionProperty;

	PropertyLocator<Vector2f> directionProperty;

	PropertyLocator<Color> renderColorProperty;

	PropertyLocator<Vector2f> sizeProperty;

	PropertyLocator<Renderer> rendererProperty;
	
	PropertyLocator<Integer> layerProperty;

	public ImageRenderableComponent(String id) {
		super(id);
		imageProperty = Properties.property(id, "image");
		positionProperty = Properties.property(id, "position");
		directionProperty = Properties.property(id, "direction");
		renderColorProperty = Properties.property(id, "color");
		sizeProperty = Properties.property(id, "size");
		rendererProperty = Properties.property("renderer");
		layerProperty = Properties.property(id, "layer");
	}

	@Handles
	public void render(Message message) {
		Renderer renderer = rendererProperty.getValue(message);

		final Vector2f position = positionProperty.getValue(entity);
		final Color renderColor = renderColorProperty.getValue(entity, Color.white);
		final Vector2f size = sizeProperty.getValue(entity, new Vector2f(1, 1));
		final Image image = imageProperty.getValue(entity);
		final float theta = (float) directionProperty.getValue(entity).getTheta();
		Integer layer = layerProperty.getValue(entity, 0);

		renderer.enqueue(new SlickCallableRenderObject(layer) {

			@Override
			public void execute(Graphics g) {
				g.pushTransform();
				{
					g.translate(position.x, position.y);
					g.scale(size.x, size.y);
					g.rotate(0, 0, theta);

					g.drawImage(image, -(image.getWidth() / 2), -(image.getHeight() / 2), renderColor);
				}
				g.popTransform();
			}
		});

	}
}
