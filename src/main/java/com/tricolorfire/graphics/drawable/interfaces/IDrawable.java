package com.tricolorfire.graphics.drawable.interfaces;

import com.tricolorfire.graphics.drawable.DrawableType;

import javafx.scene.Node;

public interface IDrawable extends IBounds,IGraphics {
	
	//获得一个可以添加的节点
	public Node getNode();

	//获得Drawable类型
	public DrawableType getType();
	
}
