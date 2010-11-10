package com.gemserk.componentsengine.android.render;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.gemserk.componentsengine.render.RenderObject;
import com.gemserk.componentsengine.render.Renderer;
import com.google.inject.Inject;

public class AndroidDrawable2dRenderer implements Renderer {

	@Inject CurrentGL currentGL;
	
	@Override
	public void render(RenderObject renderObject) {
		if (!(renderObject instanceof Rendereable2d)) 
			return;	
		
		Rendereable2d rendereable = (Rendereable2d) renderObject;
		GL10 gl = currentGL.getGl();
		
		gl.glFrontFace(GL10.GL_CCW); // OpenGL docs
		// Enable face culling.
		gl.glDisable(GL10.GL_CULL_FACE);
		//gl.glEnable(GL10.GL_CULL_FACE); // OpenGL docs
		// What faces to remove with the face culling.
		//gl.glCullFace(GL10.GL_BACK); // OpenGL docs

		// Point to our buffers
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		
		gl.glPushMatrix();
		{
			//Drawing
			gl.glTranslatef(rendereable.getPosition().x, rendereable.getPosition().y, 0f);		
			gl.glScalef(rendereable.getScale().x, rendereable.getScale().y, 1f); 			
			
			gl.glRotatef(rendereable.getAngle(), 0.0f, 0.0f, 1.0f);	//Z

			
			Sprite sprite = rendereable.getSprite();

			// Bind our only previously generated texture in this case
			Texture texture = sprite.getTexture();
			
			
			gl.glBindTexture(GL10.GL_TEXTURE_2D, texture.getTextureId());

			// Enable the vertex and texture state
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, sprite.getVertexBuffer());
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, sprite.getTextCoordBuffer());

			// Draw the vertices as triangles, based on the Index Buffer information

			gl.glDrawElements(GL10.GL_TRIANGLES, sprite.getIndexQuantity(), GL10.GL_UNSIGNED_SHORT, sprite.getIndexBuffer());
		}
		gl.glPopMatrix();

		// Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
	}

}
