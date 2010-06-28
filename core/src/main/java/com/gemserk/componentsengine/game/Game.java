/**
 * 
 */
package com.gemserk.componentsengine.game;

import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.entities.Root;
import com.gemserk.componentsengine.messages.ChildrenManagementMessageFactory;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.templates.TemplateProvider;
import com.google.inject.Inject;

public class Game {

	@Inject TemplateProvider templateProvider;
	
	@Inject @Root 
	Entity rootEntity;
	
	@Inject
	MessageQueue messageQueue;
	
	public void setRootEntity(Entity rootEntity) {
		this.rootEntity = rootEntity;
	}

	public void loadScene(String sceneName) {

		Entity entity = templateProvider.getTemplate(sceneName).instantiate("scene");
		messageQueue.enqueue(ChildrenManagementMessageFactory.addEntity(entity, rootEntity));
		
	}

}