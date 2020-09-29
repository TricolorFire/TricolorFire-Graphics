package com.tricolorfire.graphics.anchor.interfaces;

import com.tricolorfire.graphics.drawable.interfaces.IDrawable;
import com.tricolorfire.graphics.layer.LayerPane;

public interface IAdjustmentProcessor {
	
	/**
	 * 开始调整
	 * @param layerPane
	 * @param drawable
	 * @param tmpDrawable
	 */
	public void start(LayerPane layerPane, IDrawable drawable );
	
	/**
	 * 调整结束
	 * @param layerPane
	 * @param drawable
	 * @param tmpDrawable
	 */
	public void finished(LayerPane layerPane, IDrawable drawable );
	
}
