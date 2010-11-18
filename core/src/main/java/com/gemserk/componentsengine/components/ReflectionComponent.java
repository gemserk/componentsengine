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

	private static final Logger logger = LoggerFactory.getLogger(ReflectionComponent.class);
	
	static final Map<Class, Map<String, Method>> cache = new HashMap<Class, Map<String, Method>>();

	private final Map<String, Method> methods;

	public ReflectionComponent(String id) {
		super(id);
		methods = getMethods(getClass());
	}

	private static Map<String, Method> getMethods(Class<? extends ReflectionComponent> clazz) {
		Map<String, Method> cachedMethods = cache.get(clazz);
		if (cachedMethods == null) {
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
			if (methodParametersTypes.length != 1 || !(methodParametersTypes[0].equals(Message.class)))
				throw new RuntimeException("Error in component configuration (wrong parameters: " + Arrays.asList(methodParametersTypes) + ")");

			method.setAccessible(true);
			List<String> messageIds = Arrays.asList(annotation.ids());

			List<String> finalMessageIds = messageIds.isEmpty() ? Arrays.asList(method.getName()) : messageIds;

			for (String messageId : finalMessageIds) {
				if (cachedMethods.containsKey(messageId))
					throw new RuntimeException("Error in component configuration, multiple methods handle same  message.id:" + messageId);
				cachedMethods.put(messageId, method);
			}
		}

		return cachedMethods;
	}

	Object[] param = new Object[1];// avoids the creation of the array to pass parameters

	@Override
	public void handleMessage(Message message) {
		try {
			Method method = methods.get(message.getId());
			if (method != null) {
				preHandleMessage(message);
				param[0] = message;
				method.invoke(this, param);
				postHandleMessage(message);
			}
		} catch (Exception e) {
			throw new RuntimeException("error while invoking handleMessage for message : " + message.getId() + " in " + this.getId() + " in  " + this.entity.getId(), e);
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

}
