/**
 * 
 */
package com.gemserk.componentsengine.game;

import java.util.HashMap;

import com.gemserk.componentsengine.components.MessageHandler;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.scene.Scene;
import com.gemserk.componentsengine.scene.SceneProvider;
import com.google.inject.Inject;

public class Game implements MessageHandler {

	Scene currentScene;

	SceneProvider sceneProvider;

	public void loadScene(String sceneName) {

		currentScene = sceneProvider.getScene(sceneName, new HashMap<String, Object>());

	}

	@Inject
	public void setSceneProvider(SceneProvider sceneProvider) {
		this.sceneProvider = sceneProvider;
	}

	@Override
	public void handleMessage(Message message) {
		currentScene.handleMessage(message);
	}

}