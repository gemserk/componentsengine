package com.gemserk.componentsengine.commons.components;

import java.text.MessageFormat;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.annotations.EntityProperty;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.Properties;

public class LabelComponent extends FieldsReflectionComponent {

	@EntityProperty(readOnly = true, required = false)
	Color color = Color.white;

	@EntityProperty(readOnly = true)
	Vector2f position;

	@EntityProperty(readOnly = true, required = false)
	Font font;

	@EntityProperty(readOnly = true)
	String message = "";

	@EntityProperty(readOnly = true, required = false)
	Object value = null;

	@EntityProperty(readOnly = true, required = false)
	String align = "center";

	@EntityProperty(readOnly = true, required = false)
	String valign = "center";
	
	public LabelComponent(String id) {
		super(id);
	}


	@Handles
	public void render(Message message) {
		Graphics g = Properties.getValue(message, "graphics");

		String text = MessageFormat.format(this.message, value);

		if (font == null)
			font = g.getFont();

		Color currentColor = g.getColor();

		Font currentFont = g.getFont();

		int textWidth = font.getWidth(text);
		int textHeight = font.getLineHeight();

		if (font != null)
			g.setFont(font);

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
		
		g.pushTransform();
		{
			g.setColor(color);
			g.translate(position.x, position.y);
			g.drawString(text, left, top);
		}
		g.popTransform();

		g.setColor(currentColor);
		g.setFont(currentFont);
	}
}