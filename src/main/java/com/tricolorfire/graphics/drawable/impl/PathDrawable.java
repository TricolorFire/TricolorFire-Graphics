package com.tricolorfire.graphics.drawable.impl;

import com.tricolorfire.graphics.drawable.DrawableType;
import com.tricolorfire.graphics.drawable.interfaces.IDrawable;

import javafx.beans.property.DoubleProperty;
import javafx.scene.shape.Path;

public class PathDrawable extends Path implements IDrawable{

	private DoubleProperty widthProperty;
	private DoubleProperty heightProperty;
	
	private void init() {
		
	}
	
	@Override
	public DoubleProperty widthProperty() {
		return widthProperty;
	}

	@Override
	public DoubleProperty heightProperty() {
		return heightProperty;
	}

	@Override
	public Path getNode() {
		return this;
	}

	@Override
	public DrawableType getType() {
		return DrawableType.SHAPE;
	}

}
