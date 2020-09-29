package com.tricolorfire.graphics.creator;

import com.tricolorfire.graphics.drawable.interfaces.IDrawable;
import com.tricolorfire.graphics.layer.LayerPane;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class DrawableCreator implements EventHandler<MouseEvent>{
	//构造过程
	private IDrawableCreativeProcessor<IDrawable> processor;
	
	//图层页面
	private LayerPane layerPane;
	
	//
	private DrawableCreatorContext context;
	private boolean first = true;
	
	public DrawableCreator(LayerPane layerPane) {
		if(layerPane == null ) {
			throw new NullPointerException();
		}
		this.layerPane = layerPane;
		context = new DrawableCreatorContext(layerPane.getTemporaryDrawingLayer());
	}
	
	@Override
	public void handle(MouseEvent event) {
		
		//设置当前坐标
		context.setNowX(event.getX());
		context.setNowY(event.getY());
		
		//第一次点击
		if(first && event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
			processor.initTempCreate(context);
			first = false;
		} else if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
			//
			
			//
			
			//
			
		} else if(event.getEventType().equals(MouseEvent.MOUSE_MOVED)) {
			processor.move(context);
		} else if(event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
			processor.drag(context);
		}  else if(event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
			
			//将数据压入堆栈
			context.xPoints().push(event.getX());
			context.yPoints().push(event.getY());
			
			//构造临时节点
			processor.tempCreate(context);
			if(processor.isCompleted(context)) {
				//TODO 构建出drawable 返回创建完成的图像和控制器
				IDrawable drawable = processor.create(context);
				//TODO 将drawable 置入矢量层
				layerPane.getVectorLayer().addDrawable(drawable);
				//清所有临时节点
				context.getTmpNodes().clear();
			}
			
		}		
	}

	//重置构造器
	public void reset(){
		first = true;
	}

	public LayerPane getLayerPane() {
		return layerPane;
	}

	public void setLayerPane(LayerPane layerPane) {
		this.layerPane = layerPane;
		context = new DrawableCreatorContext(layerPane.getTemporaryDrawingLayer());
	}
}
