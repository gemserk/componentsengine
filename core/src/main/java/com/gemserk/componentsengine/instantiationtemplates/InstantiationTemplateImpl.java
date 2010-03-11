package com.gemserk.componentsengine.instantiationtemplates;

import java.util.Map;

import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.genericproviders.GenericProvider;
import com.gemserk.componentsengine.templates.EntityTemplate;

public class InstantiationTemplateImpl implements InstantiationTemplate {

	EntityTemplate template;

	GenericProvider genericProvider;

	public InstantiationTemplateImpl(EntityTemplate template, GenericProvider genericProvider) {
		this.template = template;
		this.genericProvider = genericProvider;
	}

	public Entity get(){
		return get(new Object[]{});
	}
	
	public Entity get(Object ...parameters){
		Map<String, Object> entityParameters = genericProvider.get(parameters);
		return template.instantiate("", entityParameters); //TODO: review id parameter
	}
}