package com.tricolorfire.graphics.anchor.interfaces;

import com.tricolorfire.graphics.ui.PenetrablePane;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public interface IDrawableControlPane {
	
	public PenetrablePane getPane();
	
	public void setAdjustmentProcessor(IAdjustmentProcessor processor);
	
	public IAdjustmentProcessor getAdjustmentProcessor();
	
	public void clearAdjustmentProcessor();
	
	static class HandlerMaker {
		public static EventHandler<MouseEvent> make(IAdjustmentProcessor processor) {
			EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if(event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
						processor.start();
					} else if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
						processor.finished();
					}
				}
			};
			return handler;
		}
	}
}