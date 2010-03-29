/**
 * 
 */
package com.gemserk.componentsengine.game;

import com.gemserk.componentsengine.components.MessageHandler;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.entities.Root;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.templates.TemplateProvider;
import com.google.inject.Inject;

public class Game implements MessageHandler {

	@Inject TemplateProvider templateProvider;
	
	@Inject @Root 
	Entity rootEntity;
	
	public void setRootEntity(Entity rootEntity) {
		this.rootEntity = rootEntity;
	}

	public void loadScene(String sceneName) {

		Entity entity = templateProvider.getTemplate(sceneName).instantiate("scene");
		
		rootEntity.addEntity(entity);
	}

	@Override
	public void handleMessage(Message message) {
		rootEntity.handleMessage(message);
	}

}