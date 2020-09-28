package com.tricolorfire.graphics.anchor;

import com.tricolorfire.graphics.drawable.interfaces.IDrawable;
import com.tricolorfire.graphics.layer.LayerPane;

import javafx.scene.Node;

public interface IDrawableControlPaneProvider {
	
	/**
	 * 判断该类型的drawable是否可以由该提供器提供
	 * @param operationLayer
	 * @param drawable
	 * @return
	 */
	public boolean accept(LayerPane layerPane,IDrawable drawable) ;
	
	/**
	 * 创建一个控制面板
	 * @param operationLayer
	 * @param drawable
	 * @return
	 */
	public Node createControlPanes(LayerPane layerPane,IDrawable drawable) ;
	
}
