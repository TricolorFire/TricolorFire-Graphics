package com.tricolorfire.graphics.anchor.impl;

import com.tricolorfire.graphics.anchor.IDrawableControlPaneProvider;
import com.tricolorfire.graphics.anchor.pane.RectangularDrawableControlPane;
import com.tricolorfire.graphics.anchor.pane.RotateControlPane;
import com.tricolorfire.graphics.drawable.interfaces.IDrawable;
import com.tricolorfire.graphics.layer.LayerPane;
import com.tricolorfire.graphics.ui.PenetrablePane;

import javafx.scene.Node;

public class DefaultControlPaneProvider implements IDrawableControlPaneProvider{

	@Override
	public Node createControlPanes(LayerPane layerPane, IDrawable drawable) {
		
		RectangularDrawableControlPane rectControl = new RectangularDrawableControlPane(drawable);
		rectControl.adaptToNewScale(layerPane.scaleXProperty());
		
		RotateControlPane rotateControl = new RotateControlPane(drawable);
		rotateControl.adaptToNewScale(layerPane.scaleXProperty());
		
		PenetrablePane pane = new PenetrablePane(); 
		pane.getChildren().addAll(rotateControl,rectControl);
		
		return pane;
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
