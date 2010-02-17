package com.gemserk.componentsengine.messages;

import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.scene.Scene;

public class Message {
	Entity entity;
	Scene scene;
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	public Scene getScene() {
		return scene;
	}
}
