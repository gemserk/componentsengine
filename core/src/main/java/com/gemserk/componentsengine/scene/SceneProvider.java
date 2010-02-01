/**
 * 
 */
package com.gemserk.componentsengine.scene;

import java.util.Map;


public interface SceneProvider {

	Scene getScene(String sceneName);

	Scene getScene(String sceneName, Map<String, Object> parameters);

}