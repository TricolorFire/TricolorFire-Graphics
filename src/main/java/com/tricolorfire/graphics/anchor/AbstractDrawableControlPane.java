package com.tricolorfire.graphics.anchor;

import com.tricolorfire.graphics.anchor.interfaces.IAdjustmentProcessor;
import com.tricolorfire.graphics.anchor.interfaces.IDrawableControlPane;
import com.tricolorfire.graphics.drawable.interfaces.IDrawable;
import com.tricolorfire.graphics.layer.LayerPane;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public abstract class AbstractDrawableControlPane implements IDrawableControlPane {
	
	private IAdjustmentProcessor processor  ;
	private EventHandler<MouseEvent> handler;
	
	private IDrawable drawable;
	private LayerPane layerPane;
	
	protected AbstractDrawableControlPane(LayerPane layerPane, IDrawable drawable) {
		this.layerPane = layerPane;
		this.drawable = drawable;
	}
	
	@Override
	public void setAdjustmentProcessor(IAdjustmentProcessor processor) {
		this.processor = processor;
		handler = HandlerMaker.make(processor , layerPane ,drawable );
		getPane().addEventHandler(MouseEvent.ANY, handler);
	}
	
	@Override
	public IAdjustmentProcessor getAdjustmentProcessor() {
		return this.processor;
	}
	
	@Override
	public void clearAdjustmentProcessor() {
		if(processor != null) {
			processor = null;
			getPane().removeEventHandler(MouseEvent.ANY, handler);
		}
	}
	
}
