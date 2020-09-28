package com.tricolorfire.graphics.anchor.impl;

import com.tricolorfire.graphics.anchor.IDrawableControllor;
import com.tricolorfire.graphics.anchor.pane.RectangularDrawableControlPane;
import com.tricolorfire.graphics.anchor.pane.RotateControlPane;
import com.tricolorfire.graphics.drawable.interfaces.IDrawable;
import com.tricolorfire.graphics.layer.LayerPane;
import com.tricolorfire.graphics.ui.PenetrablePane;

public class DefaultDrawableController implements IDrawableControllor{
	
	private PenetrablePane pane;
	
	public DefaultDrawableController(LayerPane layerPane, IDrawable drawable) {
		
		RectangularDrawableControlPane rectControl = new RectangularDrawableControlPane(drawable);
		rectControl.adaptToNewScale(layerPane.scaleXProperty());
		
		RotateControlPane rotateControl = new RotateControlPane(drawable);
		rotateControl.adaptToNewScale(layerPane.scaleXProperty());
		
		pane = new PenetrablePane(); 
		pane.getChildren().addAll(rotateControl,rectControl);
		
	}

	@Override
	public PenetrablePane getPane() {
		return this.pane;
	}

}
