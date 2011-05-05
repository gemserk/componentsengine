package gemserk.gui;

import com.gemserk.componentsengine.commons.components.DisablerComponent;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.commons.components.ImageRenderableComponent;

builder.entity {
	
	tags("imagebutton")

	def onOverSize = parameters.onOverSize ?: 1.0f
	def onPressedSize = parameters.onPressedSize ?: 1.0f
	
	property("buttonImage", parameters.buttonImage)
	property("buttonOverImage", parameters.buttonImageOver)
	property("buttonPressedImage", parameters.buttonImagePressed)
	
	property("size", parameters.size)
	
	property("buttonWidth", {(float)(entity.size.x / entity.buttonImage.width )})
	property("buttonHeight", {(float)(entity.size.y / entity.buttonImage.height)})
	
	property("buttonSize", {new Vector2f((float)entity.buttonWidth, (float)entity.buttonHeight)})
	
	parameters.bounds = new Rectangle(-(float)(entity.size.x/2), 
			-(float)(entity.size.y/2), entity.size.x, entity.size.y);
	
	parent("gemserk.gui.button", parameters)
	
	component(new DisablerComponent(new ImageRenderableComponent("image"))) {
		property("enabled", {!entity.cursorOver || !entity.enabled} )
		propertyRef("image", "buttonImage");
		propertyRef("position", "position")
		propertyRef("direction", "direction")
		propertyRef("size", "buttonSize");
	}

	component(new DisablerComponent(new ImageRenderableComponent("imageOver"))) {
		property("enabled", {entity.enabled && entity.cursorOver && !entity.pressed} )
		propertyRef("image", "buttonOverImage");
		propertyRef("position", "position")
		propertyRef("direction", "direction")
		property("size", {entity.buttonSize.copy().scale(onOverSize)});
	}

	component(new DisablerComponent(new ImageRenderableComponent("imagePressed"))) {
		property("enabled", {entity.enabled && entity.pressed} )
		propertyRef("image", "buttonPressedImage");
		propertyRef("position", "position")
		propertyRef("direction", "direction")
		property("size", {entity.buttonSize.copy().scale(onPressedSize)});
	}

}
