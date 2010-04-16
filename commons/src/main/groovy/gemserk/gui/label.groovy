package gemserk.gui;

import com.gemserk.componentsengine.commons.components.LabelComponent;

builder.entity {
	
	tags("label")
	
	property("position", parameters.position)
	property("message", parameters.message)
	property("value", parameters.value)
	property("font", parameters.font)
	property("bounds", parameters.bounds)
	
	def align = parameters.align ?: "left"
	def valign = parameters.valign ?: "top"
	
	if (align == "left") 
		property("left", {(float)(entity.position.x + entity.bounds.minX)})
	else if (align == "right") 
		property("left", {(float)(entity.position.x + entity.bounds.maxX)})
	else if (align == "center") 
		property("left", {(float)(entity.position.x)})
	
	if (valign == "top") 
		property("top", {(float)(entity.position.y + entity.bounds.minY)})
	else if (valign == "bottom") 
		property("top", {(float)(entity.position.y + entity.bounds.maxY)})
	else if (valign == "center") 
		property("top", {(float)(entity.position.y)})
	
	
	property("textPosition", {
		utils.vector(entity.left, entity.top)
	})
	
	// for debug
	//	component(new RectangleRendererComponent("renderBounds") ) {
	//		propertyRef("position", "position")
	//		propertyRef("rectangle", "bounds")
	//		property("fillColor", utils.color(0f,1f,1f,0.4f))		
	//	}
	
	component(new LabelComponent("textRenderComponent")) {
		propertyRef("position","textPosition")
		propertyRef("message","message")
		propertyRef("value", "value")
		propertyRef("font", "font")
		property("color", parameters.fontColor ?: utils.color(1,1,1,1))
		property("align", align)
		property("valign", valign)
	}
	
	
}
