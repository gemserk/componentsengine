package com.gemserk.componentsengine.templates;

import java.util.HashMap;
import java.util.Map;

public class RegistrableTemplateProvider implements TemplateProvider {

	Map<String, EntityTemplate> entityTemplates = new HashMap<String, EntityTemplate>();

	public void add(String name, EntityTemplate entityTemplate) {
		entityTemplates.put(name, entityTemplate);
	}

	@Override
	public EntityTemplate getTemplate(String name) {
		return entityTemplates.get(name);
	}

}