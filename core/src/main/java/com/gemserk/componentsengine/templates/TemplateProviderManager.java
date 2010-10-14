package com.gemserk.componentsengine.templates;

public interface TemplateProviderManager {

	void register(TemplateProvider templateProvider);

	void unregister(TemplateProvider templateProvider);

}