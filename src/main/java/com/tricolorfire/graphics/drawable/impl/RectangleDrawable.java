package com.tricolorfire.graphics.drawable.impl;

import com.tricolorfire.graphics.drawable.DrawableType;
import com.tricolorfire.graphics.drawable.interfaces.IDrawable;

import javafx.scene.shape.Rectangle;

public class RectangleDrawable extends Rectangle implements IDrawable {

	
	@Override
	public Rectangle getNode() {
		return this;
	}

	@Override
	public DrawableType getType() {
		return DrawableType.SHAPE;
	}

}
