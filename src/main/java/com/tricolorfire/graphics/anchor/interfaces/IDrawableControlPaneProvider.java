package com.tricolorfire.graphics.anchor.interfaces;

import com.tricolorfire.graphics.drawable.interfaces.IDrawable;
import com.tricolorfire.graphics.layer.LayerPane;

/**
 * 控制面板提供器
 */
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
	public IDrawableControlPane createControlPanes(LayerPane layerPane,IDrawable drawable,IDrawable tmpDrawable) ;
	
}
