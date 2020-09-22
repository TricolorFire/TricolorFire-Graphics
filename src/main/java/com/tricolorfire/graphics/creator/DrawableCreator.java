package com.tricolorfire.graphics.creator;

import com.tricolorfire.graphics.drawable.interfaces.IDrawable;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class DrawableCreator implements EventHandler<MouseEvent>{
	//构造过程
	private IDrawableCreativeProcess<IDrawable> process;
	//
	private DrawableCreatorContext context;
	private boolean first = true;
	
	public DrawableCreator(IDrawableCreativeProcess<IDrawable> context) {
		
	}
	
	@Override
	public void handle(MouseEvent event) {
		
		//第一次点击
		if(first && event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
			process.initTempCreate(context);
			first = false;
		}
		
		if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
			//
			
			//
			
			//
			
		} else if(event.getEventType().equals(MouseEvent.MOUSE_MOVED)) {
			process.move(context);
		} else if(event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
			process.drag(context);
		}  else if(event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
			
		}		
	}

	//重置构造器
	public void reset(){
		first = true;
	}
}
