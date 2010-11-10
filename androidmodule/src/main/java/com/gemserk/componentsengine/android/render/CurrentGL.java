package com.gemserk.componentsengine.android.render;

import javax.microedition.khronos.opengles.GL10;

public class CurrentGL {
	GL10 gl;
	
	public void setGl(GL10 gl) {
		this.gl = gl;
	}
	
	public GL10 getGl() {
		return gl;
	}
}