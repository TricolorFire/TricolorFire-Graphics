package com.tricolorfire.graphics.drawable.impl;

import com.tricolorfire.graphics.drawable.interfaces.IShapeDrawable;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class RectangleDrawable extends Rectangle implements IShapeDrawable {

	
	public RectangleDrawable() {
		super();
	}

	public RectangleDrawable(double x, double y, double width, double height) {
		super(x, y, width, height);
	}

	public RectangleDrawable(double width, double height, Paint fill) {
		super(width, height, fill);
	}

	public RectangleDrawable(double width, double height) {
		super(width, height);
	}

	@Override
	public Rectangle getNode() {
		return this;
	}
	
	@Override
	public RectangleDrawable copy() {
		RectangleDrawable drawable = new RectangleDrawable();
		this.loadBoundsInfoTo(drawable);
		this.loadGraphicsInfoTo(drawable);
		return drawable;
	}
}
