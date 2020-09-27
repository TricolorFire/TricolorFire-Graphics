package com.tricolorfire.graphics.anchor;

import com.tricolorfire.graphics.drawable.interfaces.IDrawable;
import com.tricolorfire.graphics.layer.LayerPane;

public interface IDrawableControlPaneProvider {
	
	public void provide(LayerPane layerPane,IDrawable drawable) ;
	
}
