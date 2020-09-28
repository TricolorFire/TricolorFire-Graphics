package com.tricolorfire.graphics.drawable.interfaces;

import com.tricolorfire.graphics.drawable.DrawableType;

import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.transform.Transform;

public interface IDrawable extends IBounds,IGraphics {
	
	public static final Color DEFAULT_SNAPSHOT_BACKGROUND_COLOR = Color.web("#00000000");
	
	//获得一个可以添加的节点
	public Node getNode();

	//获得Drawable类型
	public DrawableType getType();
	
	//获得一个拷贝
	public IDrawable copy();
	
	//绘制成栅格图
	default void draw(Canvas canvas) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Node node = getNode();
		if(node == null) {
			throw new NullPointerException("shape对象不能为空");
		}
		
		double strokeWidth = 0;
		
		if(getStroke() != null) {
			switch(getStrokeType()) {
			case INSIDE:
				strokeWidth = 0;
				break;
			case CENTERED:
				strokeWidth = getStrokeWidth();
				break;
			case OUTSIDE:
				strokeWidth = getStrokeWidth()*2;
				break;
			}
		}
		
		double scaleX = node.getScaleX();
		double scaleY = node.getScaleY();
		
		double width = widthProperty().doubleValue() + strokeWidth*2;
		double height = heightProperty().doubleValue() + strokeWidth*2;
		double x = (node.layoutXProperty().doubleValue()) - strokeWidth;
		double y = (node.layoutYProperty().doubleValue()) - strokeWidth;
		
		//用来处理Drawable本身的缩放
		x += (width-strokeWidth)*(1.0-scaleX)/2;
		y += (height-strokeWidth)*(1.0-scaleY)/2;
		
		x += node.translateXProperty().doubleValue();
		y += node.translateYProperty().doubleValue();
		
		width  *= scaleX;
		height *= scaleY;
		
		//如果有一个为0，直接返回
		if(width == 0 || height == 0) return ;
		
		//记录旋转
		double rotate = node.rotateProperty().doubleValue();
		rotateProperty().set(0);
		
		//初始化图片参数
		WritableImage writableImage ;
	    SnapshotParameters spa = new SnapshotParameters();
	    spa.setFill(DEFAULT_SNAPSHOT_BACKGROUND_COLOR);
	    spa.setTransform(Transform.scale(1, 1));
	    
	    //拍摄图形
	    writableImage = node.snapshot(spa, null);
	    
	    //栅格图片旋转
	    gc.translate(x + width/2,y + height/2);
	    gc.rotate(rotate);
	    gc.drawImage(writableImage,-width/2,-height/2);
		
	    //恢复旋转
		this.rotateProperty().set(rotate);
	}
}
