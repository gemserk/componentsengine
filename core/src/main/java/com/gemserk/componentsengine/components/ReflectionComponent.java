package com.gemserk.componentsengine.components;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.messages.Message;
import com.google.inject.internal.Maps;

@SuppressWarnings("unchecked")
public abstract class ReflectionComponent extends Component {

	protected static final Logger logger = LoggerFactory.getLogger(ReflectionComponent.class);
	static final Map<Class, Map<String, Method>> cache = new HashMap<Class, Map<String, Method>>();

	
	private final Map<String,Method> methods;
	
	
	public ReflectionComponent(String id) {
		super(id);
		methods = getMethods(getClass());
	}

	private static Map<String, Method> getMethods(Class<? extends ReflectionComponent> clazz) {
		Map<String, Method> cachedMethods = cache.get(clazz);
		if(cachedMethods==null){
			cachedMethods = buildCachedMethods(clazz);
			cache.put(clazz, cachedMethods);
		}
		
		return cachedMethods;
	}

	private static Map<String, Method> buildCachedMethods(Class<? extends ReflectionComponent> clazz) {
		Map<String, Method> cachedMethods = Maps.newHashMap();
		Method[] methods = clazz.getMethods();
		List<Method> selectedMethods = new ArrayList<Method>();

		for (Method method : methods) {
			Handles annotation = method.getAnnotation(Handles.class);
			if (annotation == null)
				continue;

			Class[] methodParametersTypes = method.getParameterTypes();
			if(methodParametersTypes.length != 1 || !(methodParametersTypes[0].equals(Message.class)))
				throw new RuntimeException("Error in component configuration (wrong parameters: "+Arrays.asList(methodParametersTypes)+")");

			
			List<String> messageIds = Arrays.asList(annotation.ids());
			if (messageIds.isEmpty()) {
				
				String name = method.getName();
				if(cachedMethods.containsKey(name))
					throw new RuntimeException("Error in component configuration, multiple methods handle same  message.id:" + name);
				
				cachedMethods.put(name, method);
			} else {
				for (String messageId : messageIds) {
					if(cachedMethods.containsKey(messageId))
						throw new RuntimeException("Error in component configuration, multiple methods handle same  message.id:" + messageId);
					
					cachedMethods.put(messageId, method);
				}
			}
		}
		
		return cachedMethods;
	}

	@Override
	public void handleMessage(Message message) {
		try {
			Method method = methods.get(message.getId());
			if (method != null) {
				preHandleMessage(message);
				method.invoke(this, message);
				postHandleMessage(message);
			}
		} catch (Exception e) {
			throw new RuntimeException("error while invoking handleMessage for message of type: " + message.getClass() + " in " + this.getId() + " in  " + this.entity.getId(), e);
		}

	}

	protected void postHandleMessage(Message message) {

	}

	protected void preHandleMessage(Message message) {

	}
	
	@Override
	public Set<String> getMessageIds() {
		return methods.keySet();
	}
	
	

//	Method getMethod(Message message) {
//		Map<String, Method> cachedMethods = cache.get(this.getClass());
//		if (cachedMethods == null) {
//			cachedMethods = new HashMap<String, Method>();
//			cache.put(getClass(), cachedMethods);
//		}
//		Method cachedMethod = cachedMethods.get(message.getId());
//		if (cachedMethod != null) {
//			return cachedMethod;
//		} else {
//			Method[] methods = this.getClass().getMethods();
//			List<Method> selectedMethods = new ArrayList<Method>();
//
//			for (Method method : methods) {
//				Handles annotation = method.getAnnotation(Handles.class);
//				if (annotation == null)
//					continue;
//
//				List<String> messageIds = Arrays.asList(annotation.ids());
//				if (messageIds.isEmpty()) {
//					if (method.getName().equals(message.getId()))
//						selectedMethods.add(method);
//				} else {
//					if (messageIds.contains(message.getId())) {
//						selectedMethods.add(method);
//					}
//				}
//			}
//			
//			if(selectedMethods.size() > 1)
//				throw new RuntimeException("Error in component configuration message.id:" + message.getId() + " entity.id:" + entity.getId() + " component.id:" + this.getId() );
//
//		
//			
//			if(selectedMethods.isEmpty())
//				return null;
//			
//			cachedMethod = selectedMethods.get(0);
//			
//			Class[] methodParametersTypes = cachedMethod.getParameterTypes();
//			if(methodParametersTypes.length != 1 || !(methodParametersTypes[0].equals(Message.class)))
//				throw new RuntimeException("Error in component configuration (wrong parameters: "+Arrays.asList(methodParametersTypes)+") message.id:" + message.getId() + " entity.id:" + entity.getId() + " component.id:" + this.getId() );
//
//			
//			
//			cachedMethods.put(message.getId(), cachedMethod);
//			return cachedMethod;
//		}
//	}

}
