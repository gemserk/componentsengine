package com.gemserk.componentsengine.templates;

import java.util.ArrayList;
import java.util.Collection;

public class TemplateProviderManagerImpl implements TemplateProvider, TemplateProviderManager {

	private Collection<TemplateProvider> templateProviders = new ArrayList<TemplateProvider>();

	@Override
	public EntityTemplate getTemplate(String name) {
		for (TemplateProvider templateProvider : templateProviders) {
			EntityTemplate template = templateProvider.getTemplate(name);
			if (template != null)
				return template;
		}
		throw new TemplateNotFoundException("Unable to load template " + name);
	}

	public void register(TemplateProvider templateProvider) {
		this.templateProviders.add(templateProvider);
	}

	@Override
	public void unregister(TemplateProvider templateProvider) {
		this.templateProviders.remove(templateProvider);
	}

}