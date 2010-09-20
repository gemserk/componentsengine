package com.gemserk.componentsengine.commons.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.components.ReflectionComponent;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.properties.PropertyLocator;
import com.gemserk.componentsengine.render.RenderQueue;
import com.gemserk.componentsengine.render.RenderQueueImpl;
import com.gemserk.componentsengine.render.SlickImageRenderObject;

public class ImageRenderableComponent extends ReflectionComponent {

	PropertyLocator<Image> imageProperty;

	PropertyLocator<Vector2f> positionProperty;

	PropertyLocator<Vector2f> directionProperty;

	PropertyLocator<Color> renderColorProperty;

	PropertyLocator<Vector2f> sizeProperty;

	PropertyLocator<RenderQueue> rendererProperty;
	
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
		RenderQueue renderQueue = rendererProperty.getValue(message);

		final Vector2f position = positionProperty.getValue(entity);
		final Color color = renderColorProperty.getValue(entity, Color.white);
		final Vector2f size = sizeProperty.getValue(entity, new Vector2f(1, 1));
		final Image image = imageProperty.getValue(entity);
		final float theta = (float) directionProperty.getValue(entity).getTheta();
		Integer layer = layerProperty.getValue(entity, 0);

		renderQueue.enqueue(new SlickImageRenderObject(layer, image, position, size, theta, color));
	}
}
