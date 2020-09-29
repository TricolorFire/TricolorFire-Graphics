package com.tricolorfire.graphics.anchor.impl;

import java.util.Set;

import com.tricolorfire.graphics.anchor.AbstractDrawableControlPane;
import com.tricolorfire.graphics.anchor.interfaces.IAdjustmentProcessor;
import com.tricolorfire.graphics.anchor.interfaces.IDrawableControlPane;
import com.tricolorfire.graphics.anchor.pane.RectangularDrawableControlPane;
import com.tricolorfire.graphics.anchor.pane.RotateControlPane;
import com.tricolorfire.graphics.coordinate.CoordinateHelper;
import com.tricolorfire.graphics.drawable.interfaces.IDrawable;
import com.tricolorfire.graphics.layer.LayerPane;
import com.tricolorfire.graphics.ui.PenetrablePane;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class DefaultDrawableControlPane extends AbstractDrawableControlPane implements IDrawableControlPane{
	
	public DefaultDrawableControlPane(LayerPane layerPane, IDrawable drawable) {
		super(layerPane,drawable);
	}

	@Override
	protected PenetrablePane createPane(LayerPane layerPane , IDrawable drawable , IDrawable tmpDrawable) {
		
		//矩形区域控制
		RectangularDrawableControlPane rectControl = new RectangularDrawableControlPane(tmpDrawable);
		rectControl.adaptToNewScale(layerPane.scaleXProperty());
		
		//旋转控制
		RotateControlPane rotateControl = new RotateControlPane(tmpDrawable);
		rotateControl.adaptToNewScale(layerPane.scaleXProperty());
		
		//控制面板
		PenetrablePane mainPane = new PenetrablePane(); 
		mainPane.getChildren().addAll(rotateControl,rectControl);
		
		//drawable 添加移动控制监听器
		addShapeMoveListener(layerPane,drawable,tmpDrawable);
		
		return mainPane;		
	}
	
	private EventHandler<MouseEvent> shapeMoveListener;
	
	public EventHandler<MouseEvent> getShapeMoveListener(){
		return shapeMoveListener;
	}
	
	//为drawable的图形添加移动监听器
	private void addShapeMoveListener(LayerPane layerPane , IDrawable drawable , IDrawable tmpDrawable) {
		Node node = drawable.getNode();
		node.setCursor(Cursor.MOVE);
		
		shapeMoveListener = new EventHandler<MouseEvent>() {
			
			private double dx,dy;
			
			@Override
			public void handle(MouseEvent event) {
				
				Point2D center = CoordinateHelper.computeCenterPosition(drawable);
				Set<IAdjustmentProcessor> processors = getAdjustmentProcessors();
				
 				if(event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
 					//开始调整
 					for(IAdjustmentProcessor processor : processors) {
 						processor.start(layerPane, drawable);
 					}
 					
 					///////////////////////////
 					//记录初始点击位置的信息
 					///////////////////////////
 					
 					double lx = drawable.getLayoutX();
 					double ly = drawable.getLayoutY();
 					Point2D realPosition = computeRealPosition(
 							center,
 							event.getX(),
 							event.getY());
 					
 					dx = lx - realPosition.getX() ;
 					dy = ly - realPosition.getY() ;
 					
 				} else if(event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
 					
 					///////////
 					//定位
 					///////////
 					
 					Point2D realPosition = computeRealPosition(
 							center,
 							event.getX() ,
 							event.getY() );
 					
 					tmpDrawable.setLocation(
 							realPosition.getX() + dx , 
 							realPosition.getY() + dy );

        		} else if(event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
        			//调整完成
 					for(IAdjustmentProcessor processor : processors) {
 						processor.finished(layerPane, drawable);
 					}
        		}
			}
			
			private Point2D computeRealPosition(Point2D center,double nowX,double nowY) {
				return CoordinateHelper.computeRealPosition(center,Math.toRadians(drawable.getRotate()),nowX, nowY);
			}
			
		};
		
		node.addEventHandler(MouseEvent.ANY, shapeMoveListener);
	}
	
}
