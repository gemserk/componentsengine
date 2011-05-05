package com.gemserk.componentsengine.android.render;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class TextureLoader {
	
	@Inject Provider<Context> context;
	@Inject CurrentGL currentGL;
//	private final GL10 gl;
	
	
	private Map<Integer, Texture> textureCache = new HashMap<Integer, Texture>();
	
	
	
	
//	public TextureLoader(Context context, GL10 gl) {
//		this.context = context;
//		this.gl = gl;	
//	}

	public Texture loadTexture(int resource){
		Texture texture = textureCache.get(resource);
		if(texture==null){
			texture = innerLoadTexture(resource);
			textureCache.put(resource, texture);
		}
		return texture;
	}
	
	public Texture innerLoadTexture(int resource) {
		GL10 gl = currentGL.getGl();
		// Get the texture from the Android resource directory
		InputStream is = context.get().getResources().openRawResource(resource);
		Bitmap bitmap = null;
		final BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
		try {
			// BitmapFactory is an Android graphics utility for images
			bitmap = BitmapFactory.decodeStream(is, null, decodeOptions);

		} finally {
			// Always clear and close
			try {
				is.close();
				is = null;
			} catch (IOException e) {
			}
		}

		
		int[] textures = new int[1];
		// Generate one texture pointer...
		gl.glGenTextures(1, textures, 0);
		// ...and bind it to our array
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

		// Create Nearest Filtered Texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

		// Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);

		// Use the Android GLUtils to specify a two-dimensional texture image from our bitmap
		
		try {
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Clean up
		bitmap.recycle();
		
		int width = decodeOptions.outWidth;
		int height = decodeOptions.outHeight;
		
		return new Texture(textures[0],width, height);
	}
}
