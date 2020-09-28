package com.tricolorfire.graphics.anchor.impl;

import com.tricolorfire.graphics.anchor.IDrawableControlPaneProvider;
import com.tricolorfire.graphics.anchor.IDrawableControlPane;
import com.tricolorfire.graphics.drawable.interfaces.IDrawable;
import com.tricolorfire.graphics.layer.LayerPane;

public class DefaultControlPaneProvider implements IDrawableControlPaneProvider{

	@Override
	public IDrawableControlPane createControlPanes(LayerPane layerPane, IDrawable drawable) {
		return new DefaultDrawableControlPane(layerPane, drawable);
	}

	@Override
	public boolean accept(LayerPane layerPane, IDrawable drawable) {
		if(layerPane == null || drawable == null) {
			throw new NullPointerException();
		}
		//判断可接受类型
		switch(drawable.getType()) {
		case SHAPE:
		case GROUP:
			return true;
		default:
			return false;
		}
	}

}
