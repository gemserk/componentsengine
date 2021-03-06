package com.gemserk.componentsengine.commons.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.components.FieldsReflectionComponent;
import com.gemserk.componentsengine.components.annotations.EntityProperty;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.render.RenderQueue;
import com.gemserk.componentsengine.slick.render.SlickCallableRenderObject;
import com.gemserk.componentsengine.utils.Container;

public class BarRendererComponent extends FieldsReflectionComponent {

	@EntityProperty(readOnly = true)
	Vector2f position;

	@EntityProperty(readOnly = true)
	Container container;

	@EntityProperty(readOnly = true, required = false)
	Float width = 15.0f;

	@EntityProperty(readOnly = true, required = false)
	Float height = 3.0f;

	@EntityProperty(readOnly = true, required = false)
	Color emptyColor = Color.red;

	@EntityProperty(readOnly = true, required = false)
	Color fullColor = Color.green;
	
	@EntityProperty(readOnly = true, required = false)
	Integer layer = 0;

	public BarRendererComponent(String id) {
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
					g.scale(width, height);

					g.setColor(emptyColor);
					g.fillRect(0, 0, 1, 1);

					g.setColor(fullColor);
					g.fillRect(0, 0, container.getCurrent() / container.getTotal(), 1);
				}
				g.popTransform();
			}
		});

	}
}