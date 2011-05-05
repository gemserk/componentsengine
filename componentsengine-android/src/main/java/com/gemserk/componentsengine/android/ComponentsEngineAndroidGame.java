package com.gemserk.componentsengine.android;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import com.gemserk.componentsengine.game.GameLoop;
import com.google.inject.Inject;

public class ComponentsEngineAndroidGame implements Game {

	@Inject GameLoop gameloop;
	
	@Override
	public void init(Context context, GL10 gl, EGLConfig config) {
	
	}

	@Override
	public void onUpdate(long delta) {
		gameloop.update((int)delta);
	}

	@Override
	public void onRender() {
		gameloop.render();
	}
	
}