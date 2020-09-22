package com.tricolorfire.graphics.drawable.impl;

import com.tricolorfire.graphics.drawable.interfaces.IShapeDrawable;

import javafx.beans.property.DoubleProperty;
import javafx.scene.shape.Path;

public class PathDrawable extends Path  implements IShapeDrawable{

	private DoubleProperty widthProperty;
	private DoubleProperty heightProperty;
	
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

}
