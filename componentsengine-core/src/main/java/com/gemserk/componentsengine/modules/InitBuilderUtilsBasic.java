package com.gemserk.componentsengine.modules;

import java.util.Map;

import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.templates.TemplateProvider;
import com.gemserk.componentsengine.utils.annotations.BuilderUtils;
import com.google.inject.Inject;

public class InitBuilderUtilsBasic {

	@Inject @BuilderUtils Map<String,Object> builderUtils;
	@Inject MessageQueue messageQueue;
	@Inject TemplateProvider templateProvider;
	

	public void config() {
		builderUtils.put("templateProvider", templateProvider);
		builderUtils.put("messageQueue", messageQueue);
	}
}