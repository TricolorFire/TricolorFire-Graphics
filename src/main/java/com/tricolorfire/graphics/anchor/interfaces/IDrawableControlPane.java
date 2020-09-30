package com.tricolorfire.graphics.anchor.interfaces;

import java.util.Set;

import com.tricolorfire.graphics.drawable.interfaces.IDrawable;
import com.tricolorfire.graphics.layer.LayerPane;
import com.tricolorfire.graphics.ui.PenetrablePane;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public interface IDrawableControlPane {
	
	public PenetrablePane getPane();
	
	public void addAdjustmentProcessor(IAdjustmentProcessor processor);
	
	public Set<IAdjustmentProcessor> getAdjustmentProcessors();
	
	public boolean removeAdjustmentProcessor(IAdjustmentProcessor processor);
	
	public void clearAdjustmentProcessor();
	
	static class HandlerMaker {
		public static EventHandler<MouseEvent> make(
				IAdjustmentProcessor processor,
				LayerPane layerPane, 
				IDrawable drawable,
				IDrawable tmpDrawable) {
			EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if(event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
						processor.start(layerPane,drawable);
					} else if(event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
						processor.adjust(layerPane,drawable);
					} else if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
						processor.finished(layerPane,drawable);
					}
				}
			};
			return handler;
		}
	}
}
