package com.tricolorfire.graphics.anchor.impl;

import java.util.Arrays;
import java.util.List;

import com.tricolorfire.graphics.anchor.IDrawableControlPaneProvider;
import com.tricolorfire.graphics.anchor.pane.RectangularDrawableControlPane;
import com.tricolorfire.graphics.anchor.pane.RotateControlPane;
import com.tricolorfire.graphics.drawable.interfaces.IDrawable;
import com.tricolorfire.graphics.layer.LayerPane;

import javafx.scene.Node;

public class DefaultControlPaneProvider implements IDrawableControlPaneProvider{

	@Override
	public List<Node> createControlPanes(LayerPane layerPane, IDrawable drawable) {
		
		RectangularDrawableControlPane pane = new RectangularDrawableControlPane(drawable);
		pane.adaptToNewScale(layerPane.scaleXProperty());
		
		RotateControlPane rpane = new RotateControlPane(drawable);
		rpane.adaptToNewScale(layerPane.scaleXProperty());
		
		return Arrays.asList(rpane,pane);
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
