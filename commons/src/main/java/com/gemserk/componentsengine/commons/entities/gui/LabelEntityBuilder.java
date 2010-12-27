package com.gemserk.componentsengine.commons.entities.gui;

import org.newdawn.slick.Font;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.commons.components.LabelComponent;
import com.gemserk.componentsengine.properties.FixedProperty;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.slick.utils.SlickUtils;
import com.gemserk.componentsengine.templates.EntityBuilder;
import com.gemserk.resources.Resource;
import com.gemserk.resources.dataloaders.StaticDataLoader;
import com.google.inject.Inject;

public class LabelEntityBuilder extends EntityBuilder {

	@Inject
	SlickUtils slick;

	@Override
	public void build() {

		tags("label");

		Resource<Font> defaultFont = new Resource<Font>(new StaticDataLoader<Font>(slick.getResources().getFonts().getDefaultFont()));

		property("position", parameters.get("position"));
		property("size", parameters.get("size"), slick.vector(1f, 1f));
		property("message", parameters.get("message"));
		property("value", parameters.get("value"));
		property("font", parameters.get("font"), defaultFont);
		property("bounds", parameters.get("bounds"));
		property("color", parameters.get("color"), slick.color(1f, 1f, 1f, 1f));
		property("layer", parameters.get("layer"), 0);

		String align = (String) (parameters.get("align") != null ? parameters.get("align") : "left");
		String valign = (String) (parameters.get("valign") != null ? parameters.get("valign") : "top");

		property("align", align);
		property("valign", valign);

		property("textPosition", new FixedProperty(entity) {
			@Override
			public Object get() {
				String align = Properties.getValue(getHolder(), "align");
				String valign = Properties.getValue(getHolder(), "valign");
				Vector2f position = Properties.getValue(getHolder(), "position");
				Rectangle bounds = Properties.getValue(getHolder(), "bounds");

				float x = 0f;
				float y = 0f;

				if ("left".equalsIgnoreCase(align))
					x = position.x + bounds.getMinX();
				else if ("right".equalsIgnoreCase(align))
					x = position.x + bounds.getMaxX();
				else
					x = position.x;

				if ("top".equalsIgnoreCase(valign))
					y = position.y + bounds.getMinY();
				else if ("bottom".equalsIgnoreCase(valign))
					y = position.y + bounds.getMaxY();
				else
					y = position.y;

				return slick.vector(x, y);
			}
		});

		component(new LabelComponent("textRenderComponent")).withProperties(new ComponentProperties() {
			{
				propertyRef("position", "textPosition");
				propertyRef("size");
				propertyRef("message");
				propertyRef("value");
				propertyRef("font");
				propertyRef("color");
				propertyRef("align");
				propertyRef("valign");
				propertyRef("layer");
			}
		});
	}
}
