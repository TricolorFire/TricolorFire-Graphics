package com.tricolorfire.graphics.creator;

import com.tricolorfire.graphics.drawable.BrushParameters;
import com.tricolorfire.graphics.drawable.interfaces.IDrawable;
import com.tricolorfire.graphics.layer.Layer;
import com.tricolorfire.graphics.layer.LayerPane;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class DrawableCreator implements EventHandler<MouseEvent>{
	//构造过程
	private IDrawableCreativeProcessor<IDrawable> processor;
	private BrushParameters brushParameters;
	
	//图层页面
	private LayerPane layerPane;
	private Layer temporaryDrawingLayer;
	
	//
	private DrawableCreatorContext context;
	private boolean first = true;
	
	public DrawableCreator(LayerPane layerPane,BrushParameters brushParameters) {
		if(layerPane == null ) {
			throw new NullPointerException();
		}
		
		this.temporaryDrawingLayer = layerPane.getTemporaryDrawingLayer();
		update(layerPane, brushParameters);
	}
	
	@Override
	public void handle(MouseEvent event) {
		
		//设置当前坐标
		context.setNowX(event.getX());
		context.setNowY(event.getY());
		
		//第一次点击
		if(first && event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
			//将数据压入堆栈
			context.xPoints().push(event.getX());
			context.yPoints().push(event.getY());
			
			//构造第一个临时图像
			IDrawable fristTempDrawable = processor.initTempCreate(context);
			
			//如果自动跟随绘制参数
			if(processor.isDefaultFollowBrushParameters()) {
				brushParameters.loadGraphicsInfoTo(fristTempDrawable);
			}
			
			//将第一个临时图像添加到临时层中
			temporaryDrawingLayer.addDrawable(fristTempDrawable);
			
			//first标记置否
			first = false;
			
		} else if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
			//将数据压入堆栈
			context.xPoints().push(event.getX());
			context.yPoints().push(event.getY());
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
				//将first标记复原
				first = true;
				
				//控制器应该是通过焦点获取
				IDrawable drawable = processor.create(context);
				
				//画笔参数载入
				if(processor.isDefaultFollowBrushParameters()) {
					brushParameters.loadGraphicsInfoTo(drawable);
				}
				
				//将drawable 置入矢量层
				layerPane.getVectorLayer().addDrawable(drawable);
				
				//清所有临时节点
				context.getTempDrawables().clear();
				
				//堆栈清理
				context.xPoints().clear();
				context.yPoints().clear();
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
		update(layerPane, brushParameters);
	}
	
	public void update(LayerPane layerPane,BrushParameters brushParameters) {
		this.layerPane = layerPane;
		this.brushParameters = brushParameters;
		context = new DrawableCreatorContext(layerPane.getTemporaryDrawingLayer(),brushParameters);
	}
}
