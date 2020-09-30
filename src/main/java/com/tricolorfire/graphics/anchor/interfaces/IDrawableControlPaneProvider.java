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
	public IDrawableControlPane createControlPanes(LayerPane layerPane,IDrawable drawable) ;
	
	/**
	 * 面板供应政策
	 */
	default DisplayPolicy getDisplayPolicy() {
		return DisplayPolicy.SECONDARY;
	}
	
	//显示策略
	public enum DisplayPolicy {
		//隐藏其他控制面板
		HIDE_OTHER_CONTROL_PANE,
		//隐藏自己
		HIDE_SELF,
		//总是在第一个显示
		FRIST,
		//次要显示
		SECONDARY,
		//最顶层
		TOP_LAYER,
		//底层
		BOTTOM_LAYER
	}
}
