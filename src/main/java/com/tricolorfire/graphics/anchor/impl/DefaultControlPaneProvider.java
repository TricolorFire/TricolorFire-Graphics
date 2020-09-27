package com.tricolorfire.graphics.anchor.impl;

import com.tricolorfire.graphics.anchor.IDrawableControlPaneProvider;
import com.tricolorfire.graphics.anchor.pane.RectangularDrawableControlPane;
import com.tricolorfire.graphics.anchor.pane.RotateControlPane;
import com.tricolorfire.graphics.drawable.interfaces.IDrawable;
import com.tricolorfire.graphics.layer.LayerPane;

public class DefaultControlPaneProvider implements IDrawableControlPaneProvider{

	@Override
	public void provide(LayerPane layerPane, IDrawable drawable) {
		RectangularDrawableControlPane pane = new RectangularDrawableControlPane(drawable);
		RotateControlPane rpane = new RotateControlPane(drawable);
		layerPane.getOperationLayer().getChildren().add(rpane);
		layerPane.getOperationLayer().getChildren().add(pane);
	}

}
