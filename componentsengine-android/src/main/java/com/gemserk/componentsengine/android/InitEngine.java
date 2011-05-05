package com.gemserk.componentsengine.android;

import com.gemserk.componentsengine.android.render.AndroidDrawable2dRenderer;
import com.gemserk.componentsengine.components.FieldsReflectionComponent;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.entities.Root;
import com.gemserk.componentsengine.modules.InitDefaultTemplateProvider;
import com.gemserk.componentsengine.modules.InitEntityManager;
import com.gemserk.componentsengine.reflection.internalfields.PropertiesInternalFields;
import com.gemserk.componentsengine.reflection.wrapper.ComponentPropertiesWrapperImpl;
import com.gemserk.componentsengine.render.RenderQueueImpl;
import com.google.inject.Inject;

public class InitEngine {

	@Inject InitEntityManager initEntityManager;
	
	@Inject RenderQueueImpl renderQueueImpl;
	
	@Inject AndroidDrawable2dRenderer drawable2dRenderer;
	
	@Inject @Root Entity entity;
	
	@Inject MessageIntermediator messageIntermediator;

	@Inject InitDefaultTemplateProvider initDefaultTemplateProvider;
	
	
	
	public void config(){
		PropertiesInternalFields.useFastClassIfPossible = false;
		entity.addComponent(messageIntermediator);
		initEntityManager.config();
		renderQueueImpl.add(drawable2dRenderer);
		initDefaultTemplateProvider.config();
		
	}
	
}
