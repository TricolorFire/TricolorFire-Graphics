package com.tricolorfire.graphics.drawable.impl;

import com.tricolorfire.graphics.drawable.DrawableType;
import com.tricolorfire.graphics.drawable.interfaces.IDrawable;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.scene.shape.Path;

public class PathDrawable extends Path implements IDrawable{

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
	public Node getNode() {
		return this;
	}

	@Override
	public DrawableType getType() {
		return DrawableType.SHAPE;
	}

}
