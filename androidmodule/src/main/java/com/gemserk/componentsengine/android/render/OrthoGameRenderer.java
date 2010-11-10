package com.gemserk.componentsengine.android.render;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.gemserk.componentsengine.android.GameRenderer;


public class OrthoGameRenderer extends GameRenderer {

	@Override
	protected void onSurfaceCreatedInner(GL10 gl, EGLConfig config) {
		gl.glEnable(GL10.GL_TEXTURE_2D); // Enable Texture Mapping ( NEW )
		gl.glShadeModel(GL10.GL_SMOOTH); // Enable Smooth Shading

		gl.glDisable(GL10.GL_DEPTH_TEST);
		gl.glDisable(GL10.GL_LIGHTING);

		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glClearDepthx(1);

		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}

	@Override
	protected void onDrawFrameInner(GL10 gl) {
		gl.glLoadIdentity(); // OpenGL docs

		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	}

	@Override
	protected void onSurfaceChangedInner(GL10 gl, int width, int height) {
		Log.d("orthogamerenderer","Screen Size: (" + width + ", " + height + ")");
		gl.glViewport(0, 0, width, height);

		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(0, width, height, 0, -1, 1);

		gl.glMatrixMode(GL10.GL_MODELVIEW);
	}
}
