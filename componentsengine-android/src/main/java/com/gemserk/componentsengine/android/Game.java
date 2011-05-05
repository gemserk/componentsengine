package com.gemserk.componentsengine.android;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

public interface Game {

	void init(Context context, GL10 gl, EGLConfig config);

	void onUpdate(long delta);

	void onRender();
	
}