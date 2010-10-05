package com.gemserk.componentsengine.slick.modules;

import java.util.Map;

import com.gemserk.componentsengine.slick.utils.SlickUtils;
import com.gemserk.componentsengine.utils.annotations.BuilderUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class InitBuilderUtilsSlick {
	@Inject
	Injector injector;

	@Inject
	SlickUtils slickUtils;
	
	@Inject @BuilderUtils Map<String,Object> builderUtils;
	
	public void config() {
		builderUtils.put("slick", slickUtils);
	}

}