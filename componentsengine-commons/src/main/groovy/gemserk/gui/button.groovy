package gemserk.gui;


import com.gemserk.componentsengine.commons.components.CursorOverDetector 
import com.gemserk.componentsengine.commons.components.PressedReleasedTriggerComponent 
import com.gemserk.componentsengine.triggers.NullTrigger;

builder.entity {
	
	tags("button")
	
	property("enabled", parameters.enabled ?: true)
	
	property("position", parameters.position)
	property("direction", utils.slick.vector(1f,0f))
	
	property("text", parameters.label)
	property("font", parameters.font)
	
	property("bounds", parameters.bounds)
	property("cursorOver", false)
	property("pressed", false)
	
	property("onEnterTrigger", parameters.onEnterTrigger ?: new NullTrigger())
	property("onLeaveTrigger", parameters.onLeaveTrigger ?: new NullTrigger())
	
	property("onPressedTrigger", parameters.onPressedTrigger ?: new NullTrigger())
	property("onReleasedTrigger", parameters.onReleasedTrigger ?: new NullTrigger())
	
	component(new CursorOverDetector("cursorOver")) {
		propertyRef("position", "position")
		propertyRef("bounds", "bounds")
		propertyRef("cursorOver", "cursorOver")
		property("eventId", "move")
		propertyRef("onEnterTrigger", "onEnterTrigger")
		propertyRef("onLeaveTrigger", "onLeaveTrigger")
	}
	
	component(new PressedReleasedTriggerComponent("pressedReleased")) {
		property("pressedEvent", "mouse.leftpressed")
		property("releasedEvent", "mouse.leftreleased")
		propertyRef("pressed", "pressed")
		propertyRef("cursorOver", "cursorOver")
		propertyRef("onPressedTrigger", "onPressedTrigger")
		propertyRef("onReleasedTrigger", "onReleasedTrigger")
	}
	
	if (parameters.label != null) {
		
		child(entity("label") {
			
			parent("gemserk.gui.label", [
				font:parameters.font,
				position:parameters.position,
				bounds:parameters.bounds,
				align:"center",
				valign:"center"
			])
			
			property("message", {entity.parent.text})
			
		})
		
	}
}
