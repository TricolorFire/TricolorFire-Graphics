package com.tricolorfire.graphics.anchor;

import javafx.beans.property.DoubleProperty;

public class Anchor {
	
	////////////////////////////
	//  
	// x,y位置的配置器
	//
	////////////////////////////
	private DoubleProperty xProperty;
	private DoubleProperty yProperty;
	
	public DoubleProperty xProperty() {
		return xProperty;
	}
	
	public DoubleProperty yProperty() {
		return yProperty;
	}
	
	public void setX(double x) {
		xProperty.set(x);
	}
	
	public double getX() {
		return xProperty.get();
	}
	
	public void setY(double y) {
		yProperty.set(y);
	}
	
	public double getY() {
		return yProperty.get();
	}
}
