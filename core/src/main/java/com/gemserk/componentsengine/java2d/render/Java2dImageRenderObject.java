package com.gemserk.componentsengine.java2d.render;

import java.awt.Image;
import java.awt.geom.Point2D;

public class Java2dImageRenderObject extends Java2dRenderObject {

	private final Image image;

	private final Point2D position;

	private final double theta;

	private final Point2D size;

	public Point2D getPosition() {
		return position;
	}

	public Point2D getSize() {
		return size;
	}

	public Image getImage() {
		return image;
	}

	public double getTheta() {
		return theta;
	}

	public Java2dImageRenderObject(int layer, Image image, float x, float y, float w, float h, double theta) {
		super(layer);
		this.image = image;
		this.position = new Point2D.Float(x, y);
		this.size = new Point2D.Float(w, h);
		this.theta = theta;
	}
}