package com.tricolorfire.graphics.drawable;

import com.tricolorfire.graphics.drawable.impl.EllipseDrawable;
import com.tricolorfire.graphics.drawable.impl.LineDrawable;
import com.tricolorfire.graphics.drawable.impl.PolygonDrawable;
import com.tricolorfire.graphics.drawable.impl.RectangleDrawable;

public class ShapeDrawableFactory {
	
	//从左上角创建一个椭圆
	public static EllipseDrawable createEllipseFromTopLeftCorner(double x,double y,double radiusX, double radiusY) {
		EllipseDrawable ellipse = new EllipseDrawable(radiusX,radiusY,radiusX,radiusY);
		ellipse.setLocation(x, y);
		return ellipse;
	}
	
	//从左上角创建一个矩形
	public static RectangleDrawable createRectangleFromTopLeftCorner(double x,double y,double width,double height) {
		RectangleDrawable rectangle = new RectangleDrawable(width, height);
		rectangle.setLocation(x, y);
		return rectangle;
	}
	
	//创建一个多边形
	public static PolygonDrawable createPolygonFromTopLeftCorner(double x,double y,double[] points) {
		PolygonDrawable polygon = new PolygonDrawable(points);
		polygon.setLocation(x, y);
		return polygon;
	}
	
	public static LineDrawable 
}
