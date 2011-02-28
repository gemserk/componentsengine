package com.gemserk.componentsengine.modules;

import com.gemserk.resources.ResourceManager;
import com.gemserk.resources.ResourceManagerImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ResourcesManagerModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(ResourceManager.class).to(ResourceManagerImpl.class).in(Singleton.class);		
	}
}