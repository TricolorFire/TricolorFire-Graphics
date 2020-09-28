package com.tricolorfire.graphics.manager;

import com.tricolorfire.graphics.drawable.interfaces.IDrawable;

import javafx.beans.property.BooleanProperty;

public interface IDrawableOperation {
	
	//获取绑定的Drawable
	public IDrawable getDrawable();
	
	//判断是不是被选中的
	public boolean isSelected();
	
	//可以选择的
	public BooleanProperty selectableProperty();
	
}
