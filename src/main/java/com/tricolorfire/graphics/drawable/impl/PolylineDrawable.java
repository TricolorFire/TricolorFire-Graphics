package com.tricolorfire.graphics.drawable.impl;

import com.tricolorfire.graphics.drawable.DrawableType;
import com.tricolorfire.graphics.drawable.interfaces.IDrawable;

import javafx.beans.property.DoubleProperty;
import javafx.scene.shape.Polyline;

public class PolylineDrawable extends Polyline implements IDrawable{

	@Override
	public DoubleProperty widthProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DoubleProperty heightProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Polyline getNode() {
		return this;
	}

	@Override
	public DrawableType getType() {
		return DrawableType.SHAPE;
	}

}
