package com.tricolorfire.graphics.coordinate;

import com.tricolorfire.graphics.drawable.interfaces.IBounds;

import javafx.geometry.Point2D;
import javafx.scene.layout.Region;

public class CoordinateHelper {
	
	//计算node中心位置信息
	public static Point2D computeCenterPosition(final Region region) {
		
		double px = region.getLayoutX()+ region.getTranslateX();
		double py = region.getLayoutY()+ region.getTranslateY();
		
		double halfWidth = region.getWidth()/2;
		double halfheight = region.getHeight()/2;
	
		double centerX = px + halfWidth;
		double centerY = py + halfheight;
		
		return new Point2D(centerX, centerY);
	}
	
	//计算shapeDrawable中心位置信息
	public static Point2D computeCenterPosition(final IBounds bounds) {
		
		double px = bounds.layoutXProperty().doubleValue()
				+ bounds.translateXProperty().doubleValue();
		double py = bounds.layoutYProperty().doubleValue()
				+ bounds.translateYProperty().doubleValue();
		
		double halfWidth = bounds.widthProperty().doubleValue()/2;
		double halfheight = bounds.heightProperty().doubleValue()/2;
	
		double centerX = px + halfWidth;
		double centerY = py + halfheight;
		
		return new Point2D(centerX, centerY);
	}
	
	//计算图形旋转后真实的位置信息
	public static Point2D computeRealPosition(
			final Point2D center,
			final double rotate,
			final double nowX,
			final double nowY) {
		
		Point2D tcenter;
		if(center == null) {
			tcenter = Point2D.ZERO;
		} else {
			tcenter = new Point2D(center.getX(),center.getY());
		}
		
		//计算正弦、余弦
		double sin = Math.sin(rotate);
		double cos = Math.cos(rotate);
		
		//移到原点处
		double tx = nowX - tcenter.getX();
		double ty = nowY - tcenter.getY();
		
		//还原真实的坐标
		double realX = tx*cos - ty*sin + tcenter.getX();
		double realY = tx*sin + ty*cos + tcenter.getY();
		
		return new Point2D(realX,realY);
	}
	
	public enum AxisType {
		X,
		Y,
	}
	
	//计算图形旋转后，相对于左上顶点的的位置变化数据
	public static Point2D computeDeltaLeftTopPosition(
			final Point2D start,
			final Point2D now,
			final double width,
			final double height,
			final double radians,
			final AxisType axis) {
		
		double sin = Math.sin(radians);
		double cos = Math.cos(radians);

		double x = now.getX();
		double y = now.getY();
		
		double sx = width/2;
		double sy = height/2;
		
		Point2D oldLT = new Point2D(
				 sx*cos + sy*sin,
				-sx*sin + sy*cos);
		
		double dx = (x - start.getX())/2;
		double dy = (y - start.getY())/2;
		
		int xp = -1;
		int yp = -1;
		
		switch(axis) {
		case X:dy=0;yp=1;break;
		case Y:dx=0;xp=1;break;
		}
		
		double ex = sx + dx;
		double ey = sy + dy;
		
		Point2D newLT = new Point2D(
				 ex*cos + ey*sin - dx,
				-ex*sin + ey*cos - dy);
		
		Point2D sub = newLT.subtract(oldLT);
		
		return new Point2D(xp*sub.getX(),yp*sub.getY());
	}
}
