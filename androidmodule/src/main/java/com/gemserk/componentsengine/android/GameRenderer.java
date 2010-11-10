package com.gemserk.componentsengine.android;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import roboguice.inject.ContextScope;
import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.os.SystemClock;

import com.gemserk.componentsengine.android.render.CurrentGL;
import com.google.inject.Inject;

public class GameRenderer implements Renderer {
	
		private long lastTime;

		@Inject
		private Context context;
		
		@Inject CurrentGL currentGL;
		@Inject ContextScope contextScope;
		
		@Inject 
		Game game;
		
		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			onSurfaceCreatedInner(gl, config);
			contextScope.enter(context);
			currentGL.setGl(gl);
			game.init(context, gl, config);
			lastTime = SystemClock.elapsedRealtime();
		}
		
		
		@Override
		public void onDrawFrame(GL10 gl) {
			long currentTime = SystemClock.elapsedRealtime();
		
			long delta = currentTime - lastTime;
			lastTime = currentTime;
		
			game.onUpdate(delta);
			
			onDrawFrameInner(gl);
			
			game.onRender();
		}

		public void onSurfaceChanged(GL10 gl, int width, int height) {
			onSurfaceChangedInner(gl, width, height);
		}
		
		protected void onSurfaceCreatedInner(GL10 gl, EGLConfig config) {
			
		}

		protected void onDrawFrameInner(GL10 gl) {
			
		}

		protected void onSurfaceChangedInner(GL10 gl, int width, int height) {
			
		}
		
	}