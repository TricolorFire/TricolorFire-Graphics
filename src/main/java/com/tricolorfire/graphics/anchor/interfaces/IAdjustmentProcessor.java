package com.tricolorfire.graphics.anchor.interfaces;

import com.tricolorfire.graphics.drawable.interfaces.IDrawable;
import com.tricolorfire.graphics.layer.LayerPane;

public interface IAdjustmentProcessor {
	
	public void start(LayerPane layerPane, IDrawable drawable);
	
	public void finished(LayerPane layerPane, IDrawable drawable);
	
}
