package com.tricolorfire.graphics.anchor.impl;

import com.tricolorfire.graphics.anchor.AbstractDrawableControlPane;
import com.tricolorfire.graphics.anchor.interfaces.IDrawableControlPane;
import com.tricolorfire.graphics.anchor.pane.RectangularDrawableControlPane;
import com.tricolorfire.graphics.anchor.pane.RotateControlPane;
import com.tricolorfire.graphics.drawable.interfaces.IDrawable;
import com.tricolorfire.graphics.layer.LayerPane;
import com.tricolorfire.graphics.ui.PenetrablePane;

public class DefaultDrawableControlPane extends AbstractDrawableControlPane implements IDrawableControlPane{
	
	public DefaultDrawableControlPane(LayerPane layerPane, IDrawable drawable ,IDrawable tmpDrawable) {
		super(layerPane,drawable,tmpDrawable);
	}

	@Override
	protected PenetrablePane createPane(LayerPane layerPane , IDrawable drawable) {
		//矩形区域控制
		RectangularDrawableControlPane rectControl = new RectangularDrawableControlPane(drawable);
		rectControl.adaptToNewScale(layerPane.scaleXProperty());
		
		//旋转控制
		RotateControlPane rotateControl = new RotateControlPane(drawable);
		rotateControl.adaptToNewScale(layerPane.scaleXProperty());
		
		//控制面板
		PenetrablePane mainPane = new PenetrablePane(); 
		mainPane.getChildren().addAll(rotateControl,rectControl);
		
		return mainPane;		
	}
	
}
