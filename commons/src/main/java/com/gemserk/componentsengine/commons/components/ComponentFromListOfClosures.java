package com.gemserk.componentsengine.commons.components;

import groovy.lang.Closure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.messages.Message;

public class ComponentFromListOfClosures extends Component{

	private final Map<Class<? extends Message>,Closure> closures = new HashMap<Class<? extends Message>, Closure>();

	public ComponentFromListOfClosures(String id,List<Closure> closuresList) {
		super(id);
		for (Closure closure : closuresList) {
			Class[] parameterTypes = closure.getParameterTypes();
			if(parameterTypes.length != 1)
				throw new RuntimeException("Wrong number of parameters: " + parameterTypes);
			
			Class messageClassCandidate = parameterTypes[0];
			if(!Message.class.isAssignableFrom(messageClassCandidate))
				throw new RuntimeException("Wrong type of parameter: " + messageClassCandidate);
			
			
			Closure previous = closures.put(messageClassCandidate, closure);
			if(previous != null)
				throw new RuntimeException("Multiple assignments to same Message Type: (" + closure.getParameterTypes()[0] +")");
		}
	}
	
	@Override
	public void handleMessage(Message message) {
		Closure messageHandler = closures.get(message.getClass());
		if(messageHandler==null)
			return;
		
		messageHandler.setDelegate(this);
		messageHandler.call(message);
	}
	
	
	
	
}
