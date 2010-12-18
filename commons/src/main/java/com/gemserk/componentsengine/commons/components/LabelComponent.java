package com.gemserk.componentsengine.commons.components;

import java.text.MessageFormat;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.components.FieldsReflectionComponent;
import com.gemserk.componentsengine.components.annotations.EntityProperty;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.render.RenderQueue;
import com.gemserk.componentsengine.slick.render.SlickCallableRenderObject;
import com.gemserk.resources.Resource;

public class LabelComponent extends FieldsReflectionComponent {

	@EntityProperty(readOnly = true, required = false)
	Color color = Color.white;

	@EntityProperty(readOnly = true)
	Vector2f position;

	@EntityProperty(readOnly = true, required=false)
	Resource<Font> font;

	@EntityProperty(readOnly = true)
	String message = "";

	@EntityProperty(readOnly = true, required = false)
	Object value = null;

	@EntityProperty(readOnly = true, required = false)
	String align = "center";

	@EntityProperty(readOnly = true, required = false)
	String valign = "center";
	
	@EntityProperty(readOnly = true, required = false)
	int layer = 0;
	
	@EntityProperty(readOnly = true, required = false)
	Vector2f size = new Vector2f(1,1);
	
	public LabelComponent(String id) {
		super(id);
	}

	@Handles
	public void render(Message message) {
		RenderQueue renderQueue = Properties.getValue(message, "renderer");
		final String text = MessageFormat.format(this.message, value);
		
		final Font fnt = font.get();
		
		int textWidth = fnt.getWidth(text);
		int textHeight = fnt.getLineHeight();

		float left = 0.0f;

		if (align.equals("center"))
			left = -textWidth / 2;
		else if (align.equals("right"))
			left = -textWidth;
		
		float top = 0.0f;

		if (valign.equals("center"))
			top = -textHeight / 2;
		else if (valign.equals("bottom"))
			top = -textHeight;

		final float finalLeft = left;
		final float finalTop = top;
		
		renderQueue.enqueue(new SlickCallableRenderObject(layer) {

			@Override
			public void execute(Graphics g) {

				Color currentColor = g.getColor();

				g.setFont(fnt);
				
				g.pushTransform();
				{
					g.setColor(color);
					g.translate(position.x, position.y);
					g.scale(size.x, size.y);
					g.drawString(text, finalLeft, finalTop);
				}
				g.popTransform();

				g.setColor(currentColor);
			}
		});

		
	}
}