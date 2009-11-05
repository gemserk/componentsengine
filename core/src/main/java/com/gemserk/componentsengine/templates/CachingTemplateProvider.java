package com.gemserk.componentsengine.templates;

import java.util.HashMap;
import java.util.Map;

public class CachingTemplateProvider implements TemplateProvider {

	Map<String, EntityTemplate> templates = new HashMap<String, EntityTemplate>();
	
	TemplateProvider delegate;
	
	public CachingTemplateProvider() {
	}
	
	public CachingTemplateProvider(TemplateProvider delegate) {
		this.delegate = delegate;
	}

	
	public void setDelegate(TemplateProvider delegate) {
		this.delegate = delegate;
	}

	@Override
	public EntityTemplate getTemplate(String name) {
		EntityTemplate template = templates.get(name);
		if(template!=null)
			return template;
		
		template = delegate.getTemplate(name);
		templates.put(name, template);
		return template;
	}

}
