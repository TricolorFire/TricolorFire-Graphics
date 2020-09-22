package com.tricolorfire.graphics.drawable;

import com.tricolorfire.graphics.drawable.impl.CircleDrawable;
import com.tricolorfire.graphics.drawable.impl.EllipseDrawable;
import com.tricolorfire.graphics.drawable.impl.LineDrawable;
import com.tricolorfire.graphics.drawable.impl.PolygonDrawable;
import com.tricolorfire.graphics.drawable.impl.PolylineDrawable;
import com.tricolorfire.graphics.drawable.impl.RectangleDrawable;

public class ShapeDrawableFactory {
	
	//创建一个矩形
	public static RectangleDrawable createRectangleFromTopLeftCorner(double x,double y,double width,double height) {
		RectangleDrawable rectangle = new RectangleDrawable(width, height);
		rectangle.setLocation(x, y);
		return rectangle;
	}
	
	//创建一个圆
	public static CircleDrawable createCircleFromTopLeftCorner(double x,double y,double radius) {
		CircleDrawable circle = new CircleDrawable(radius,radius,radius);
		circle.setLocation(x, y);
		return circle;
	}
	
	//创建一个椭圆
	public static EllipseDrawable createEllipseFromTopLeftCorner(double x,double y,double radiusX, double radiusY) {
		EllipseDrawable ellipse = new EllipseDrawable(radiusX,radiusY,radiusX,radiusY);
		ellipse.setLocation(x, y);
		return ellipse;
	}
	
	//创建一个多边形
	public static PolygonDrawable createPolygonFromTopLeftCorner(double x,double y,double[] points) {
		PolygonDrawable polygon = new PolygonDrawable(points);
		polygon.setLocation(x, y);
		return polygon;
	}
	
	//创建一个折线
	public static PolylineDrawable createPolylineFromTopLeftCorner(double x,double y,double[] points) {
		PolylineDrawable polyline = new PolylineDrawable(points);
		polyline.setLocation(x, y);
		return polyline;
	}
	
	//创建一条线
	public static LineDrawable createLineFromTopLeftCorner(double x,double y,double ex,double ey) {
		LineDrawable line = new LineDrawable(x,y,ex-x,ey-y);
		line.setLocation(x, y);
		return line;
	}
}
