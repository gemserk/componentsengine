package com.gemserk.componentsengine.components;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gemserk.componentsengine.messages.Message;

@SuppressWarnings("unchecked")
public abstract class ReflectionComponent extends Component{

	
	protected static final Logger logger = LoggerFactory.getLogger(ReflectionComponent.class);
	static final Map<Class,Map<Class,Method>> cache = new HashMap<Class,Map<Class,Method>>();
	
	public ReflectionComponent(String id) {
		super(id);
	}
	
	@Override
	public void handleMessage(Message message) {
		try {
			Method method = getMethod(message);
			if(method!=null)
			{
				preHandleMessage(message);
				method.invoke(this, message);
				postHandleMessage(message);
			}
		} catch (Exception e) {
			throw new RuntimeException("error while invoking handleMessage for message of type: " + message.getClass(), e);
		}
		
	}
	
	protected void postHandleMessage(Message message) {
		
	}

	protected void preHandleMessage(Message message) {
		
	}

	Method getMethod(Message message){
		Map<Class,Method> methods = cache.get(this.getClass());
		if(methods==null){
			methods = new HashMap<Class, Method>();
			cache.put(getClass(), methods);
		}
		if(methods.containsKey(message.getClass())){
			Method method = methods.get(message.getClass());
			return method;
		}
		else
		{
			Method method = null;
			try {
				method = this.getClass().getMethod("handleMessage", message.getClass());
			} catch (Exception e) {
				logger.trace("caching method not found for reflectioncomponent",e);
			}
			methods.put(message.getClass(), method);
			return method;
		}
	}

}
