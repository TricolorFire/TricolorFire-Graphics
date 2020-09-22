package com.tricolorfire.graphics.drawable.interfaces;

import com.tricolorfire.graphics.drawable.DrawableType;

import javafx.scene.shape.Shape;

public interface IShapeDrawable extends IDrawable{

	//获得一个可以添加的节点
	public Shape getNode();

	//获得Drawable类型
	default DrawableType getType() {
		return DrawableType.SHAPE;
	}
	
}
