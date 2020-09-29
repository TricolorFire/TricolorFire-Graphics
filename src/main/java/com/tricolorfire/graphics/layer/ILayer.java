package com.tricolorfire.graphics.layer;

import com.tricolorfire.graphics.drawable.interfaces.IDrawable;

import javafx.collections.ObservableList;

public interface ILayer {
	
	//放置Drawable
	public boolean addDrawable(IDrawable drawable);
	
	//添加drawable
	public boolean addAllDrawable(IDrawable...drawables);
	
	//移除Drawable
	public boolean removeDrawable(IDrawable drawable);
	
	//获得节点
	public ObservableList<IDrawable> getDrawables();
	
	//清除掉所有的Drawable
	public void clearDrawable();
}
