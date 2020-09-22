package com.tricolorfire.graphics.layer;

import com.tricolorfire.graphics.drawable.interfaces.IDrawable;

public interface ILayer {
	
	//放置Drawable
	public void addDrawable(IDrawable drawable);
	
	//添加drawable
	public void addAllDrawable(IDrawable...drawables);
	
	//移除Drawable
	public void removeDrawable(IDrawable drawable);
	
	//清除掉所有的Drawable
	public void clearDrawable();
}
