package gemserk.gui;

import com.gemserk.componentsengine.commons.components.LabelComponent;

builder.entity {
	
	tags("label")
	
	property("position", parameters.position)
	property("size", parameters.size ?: utils.slick.vector(1,1))
	property("message", parameters.message)
	property("value", parameters.value)
	property("font", parameters.font ?: utils.slick.resources.fonts.defaultFont)
	property("bounds", parameters.bounds)
	property("color", parameters.fontColor ?: utils.slick.color(1,1,1,1))
	
	def align = parameters.align ?: "left"
	def valign = parameters.valign ?: "top"
	
	if (align == "left") 
		property("left", {(float)(entity.position.x + entity.bounds.minX)})
	else if (align == "right") 
		property("left", {(float)(entity.position.x + entity.bounds.maxX)})
	else // center 
	property("left", {(float)(entity.position.x)})
	
	if (valign == "top") 
		property("top", {(float)(entity.position.y + entity.bounds.minY)})
	else if (valign == "bottom") 
		property("top", {(float)(entity.position.y + entity.bounds.maxY)})
	else // center
	property("top", {(float)(entity.position.y)})
	
	
	property("textPosition", {
		utils.slick.vector(entity.left, entity.top)
	})
	
	// for debug
	//	component(new RectangleRendererComponent("renderBounds") ) {
	//		propertyRef("position", "position")
	//		propertyRef("rectangle", "bounds")
	//		property("fillColor", utils.color(0f,1f,1f,0.4f))		
	//	}
	
	component(new LabelComponent("textRenderComponent")) {
		propertyRef("position","textPosition")
		propertyRef("size", "size")
		propertyRef("message","message")
		propertyRef("value", "value")
		propertyRef("font", "font")
		propertyRef("color", "color")
		property("align", align)
		property("valign", valign)
		property("layer",parameters.layer ?:0)
	}
	
	
}
