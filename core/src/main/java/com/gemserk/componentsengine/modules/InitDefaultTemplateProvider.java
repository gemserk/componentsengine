package com.gemserk.componentsengine.modules;

import com.gemserk.componentsengine.templates.RegistrableTemplateProvider;
import com.gemserk.componentsengine.templates.TemplateProviderManager;
import com.google.inject.Inject;

public class InitDefaultTemplateProvider {

	@Inject
	TemplateProviderManager templateProviderManager;

	@Inject
	RegistrableTemplateProvider registrableTemplateProvider;

	public void config() {
		templateProviderManager.register(registrableTemplateProvider);
	}

}