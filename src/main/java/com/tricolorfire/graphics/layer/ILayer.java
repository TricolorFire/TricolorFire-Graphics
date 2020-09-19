package com.tricolorfire.graphics.layer;

import com.tricolorfire.graphics.drawable.interfaces.IDrawable;

public interface ILayer {
	
	//放置Drawable
	public void addDrawable(IDrawable drawable);
	
	//移除Drawable
	public void removeDrawable(IDrawable drawable);
	
}
