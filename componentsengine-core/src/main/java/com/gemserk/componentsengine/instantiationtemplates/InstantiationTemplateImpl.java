package com.gemserk.componentsengine.instantiationtemplates;

import java.util.Map;

import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.genericproviders.GenericProvider;
import com.gemserk.componentsengine.templates.EntityTemplate;

public class InstantiationTemplateImpl implements InstantiationTemplate {

	String id;
	EntityTemplate template;
	GenericProvider genericProvider;

	public InstantiationTemplateImpl(EntityTemplate template, GenericProvider genericProvider) {
		this("", template, genericProvider);
	}

	private InstantiationTemplateImpl(String id, EntityTemplate template, GenericProvider genericProvider) {
		this.id = id;
		this.genericProvider = genericProvider;
		this.template = template;
	}
	
	public Entity get(){
		return get(new Object[]{});
	}
	
	public Entity get(Object ...parameters){
		Map<String, Object> entityParameters = genericProvider.get(parameters);
		return template.instantiate(id, entityParameters); 
	}
	
	public InstantiationTemplate withId(String id) {
		return new InstantiationTemplateImpl(id, template, genericProvider);
	}
	
}