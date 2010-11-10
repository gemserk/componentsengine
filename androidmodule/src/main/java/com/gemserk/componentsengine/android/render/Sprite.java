package com.gemserk.componentsengine.android.render;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Sprite {
	// Our vertices.
	private float[] vertices;

	private float textCoords[] = { 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, };

	// The order we like to connect them.
	private short[] indices = { 0, 1, 3, 0, 3, 2 };

	// Our vertex buffer.
	private FloatBuffer vertexBuffer;

	// Our index buffer.
	private ShortBuffer indexBuffer;

	private FloatBuffer textCoordBuffer;

	private Texture texture;

	public Sprite(Texture texture) {
		this.texture = texture;
		
		float halfWidth = texture.getWidth() /2f;
		float halfHeight = texture.getHeight()/2f;
		
		vertices = new float[] { -halfWidth, -halfHeight, 1.0f, // Vertex 0
				halfWidth, -halfHeight, 1.0f, // v1
				-halfWidth, halfHeight, 1.0f, // v2
				halfWidth, halfHeight, 1.0f, // v3
		};
		
		
		// a float is 4 bytes, therefore we multiply the number if
		// vertices with 4.
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		ByteBuffer tbb = ByteBuffer.allocateDirect(textCoords.length * 4);
		tbb.order(ByteOrder.nativeOrder());
		textCoordBuffer = tbb.asFloatBuffer();
		textCoordBuffer.put(textCoords);
		textCoordBuffer.position(0);

		// short is 2 bytes, therefore we multiply the number if
		// vertices with 2.
		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		indexBuffer = ibb.asShortBuffer();
		indexBuffer.put(indices);
		indexBuffer.position(0);
	}

	/**
	 * This function draws our square on screen.
	 * 
	 * @param gl
	 */
	public void draw(GL10 gl) {
		gl.glFrontFace(GL10.GL_CCW); // OpenGL docs
		// Enable face culling.
		gl.glEnable(GL10.GL_CULL_FACE); // OpenGL docs
		// What faces to remove with the face culling.
		gl.glCullFace(GL10.GL_BACK); // OpenGL docs

		// Bind our only previously generated texture in this case
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture.getTextureId());

		// Point to our buffers
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		// Enable the vertex and texture state
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textCoordBuffer);

		// Draw the vertices as triangles, based on the Index Buffer information

		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_SHORT, indexBuffer);

		// Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}

	public FloatBuffer getVertexBuffer() {
		return vertexBuffer;
	}

	public void setVertexBuffer(FloatBuffer vertexBuffer) {
		this.vertexBuffer = vertexBuffer;
	}

	public ShortBuffer getIndexBuffer() {
		return indexBuffer;
	}

	public void setIndexBuffer(ShortBuffer indexBuffer) {
		this.indexBuffer = indexBuffer;
	}

	public FloatBuffer getTextCoordBuffer() {
		return textCoordBuffer;
	}

	public void setTextCoordBuffer(FloatBuffer textCoordBuffer) {
		this.textCoordBuffer = textCoordBuffer;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public int getIndexQuantity(){
		return indices.length;
	}
	
	public int getWidth() {
		return texture.getWidth();
	}
	
	public int getHeight() {
		return texture.getHeight();
	}
	
}
