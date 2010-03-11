package com.gemserk.componentsengine.commons.components;

import java.text.MessageFormat;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.annotations.EntityProperty;
import com.gemserk.componentsengine.commons.components.FieldsReflectionComponent;
import com.gemserk.componentsengine.messages.SlickRenderMessage;

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

	public LabelComponent(String id) {
		super(id);
	}

	public void handleMessage(SlickRenderMessage message) {
		Graphics g = message.getGraphics();

		String text = MessageFormat.format(this.message, value);

		if (font == null)
			font = g.getFont();

		Color currentColor = g.getColor();

		Font currentFont = g.getFont();

		int textWidth = font.getWidth(text);
		int textHeight = font.getLineHeight();

		if (font != null)
			g.setFont(font);

		g.pushTransform();
		{
			g.setColor(color);
			g.translate(position.x, position.y);
			g.drawString(text, -textWidth / 2, -textHeight / 2);
		}
		g.popTransform();

		g.setColor(currentColor);
		g.setFont(currentFont);
	}
}