package com.tricolorfire.graphics.anchor.impl;

import com.tricolorfire.graphics.anchor.AbstractDrawableControlPane;
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
	
	private IDrawable drawable;
	
	public DefaultDrawableControlPane(LayerPane layerPane, IDrawable drawable) {
		super(layerPane,drawable);
	}

	@Override
	protected PenetrablePane createPane(LayerPane layerPane , IDrawable drawable) {
		
		this.drawable = drawable;
		
		//矩形区域控制
		RectangularDrawableControlPane rectControl = new RectangularDrawableControlPane(drawable);
		rectControl.adaptToNewScale(layerPane.scaleXProperty());
		
		//旋转控制
		RotateControlPane rotateControl = new RotateControlPane(drawable);
		rotateControl.adaptToNewScale(layerPane.scaleXProperty());
		
		//控制面板
		PenetrablePane mainPane = new PenetrablePane(); 
		mainPane.getChildren().addAll(rotateControl,rectControl);
		
		//drawable 添加移动控制监听器
		//addShapeMoveListener();
		
		return mainPane;		
	}
	
	/*//为drawable的图形添加移动监听器
	private EventHandler<MouseEvent> shapeMoveListener;
	private void addShapeMoveListener() {
		Node node = drawable.getNode();
		node.setCursor(Cursor.MOVE);
		
		shapeMoveListener = new EventHandler<MouseEvent>() {
			
			private double dx,dy;

			@Override
			public void handle(MouseEvent event) {

				Point2D center = CoordinateHelper.computeCenterPosition(drawable);
				
 				if(event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
 					
 					Point2D realPosition = computeRealPosition(
 							center,
 							event.getX(),
 							event.getY());
 					
 					dx = - event.getX(); //realPosition.getX() ;
 					dy = - event.getY(); //realPosition.getY() ;
 					
 				} else if(event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
 					
 					//定位
 					double tx = drawable.getLayoutX();
 					double ty = drawable.getLayoutY();
 					
 					Point2D realPosition = computeRealPosition(
 							center,
 							tx + event.getX() + dx,
 							ty + event.getY() + dy);
 					
 					drawable.setLocation(
 							realPosition.getX() , 
 							realPosition.getY() );

        		} else if(event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
        			
        		}
			}

			private Point2D computeRealPosition(Point2D center,double nowX,double nowY) {
				return CoordinateHelper.computeRealPosition(center,Math.toRadians(drawable.getRotate()),nowX, nowY);
			}
		};
		
		node.addEventHandler(MouseEvent.ANY, shapeMoveListener);
	}
	*/
}
