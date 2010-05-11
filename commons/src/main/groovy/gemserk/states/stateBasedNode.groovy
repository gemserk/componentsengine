package gemserk.states

import com.gemserk.componentsengine.messages.SlickRenderMessage;
import com.gemserk.componentsengine.commons.components.ProcessingDisablerComponent 

builder.entity("statebasednode") {
	
	property("enabled", parameters.enabled != null ? parameters.enabled : false)
	
	component(utils.components.genericComponent(id:"stateBasedNodeEnabledHandler", messageId:"changeNodeState"){ message ->
		entity.enabled = message.states.contains(entity.id)
	})
	
	component(new ProcessingDisablerComponent("disableStateComponent")){
		propertyRef("enabled","enabled")
		property("exclusions",parameters.exclusions ?: [])
	}
	
}

