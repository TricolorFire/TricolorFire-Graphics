package com.tricolorfire.graphics.drawable.interfaces;

import javafx.beans.property.DoubleProperty;

public interface IBounds {
	
	//x位置
	public DoubleProperty layoutXProperty();
	
	default void setLayoutX(double x) {
		this.layoutXProperty().set(x);
	}
	default double getLayoutX() {
		return this.layoutXProperty().get();
	}
	
	//y位置
	public DoubleProperty layoutYProperty();
	
	default void setLayoutY(double y) {
		this.layoutYProperty().set(y);
	}
	
	default double getLayoutY() {
		return this.layoutYProperty().get();
	}
	
	//设置位置
	default void setLocation(double x,double y) {
		this.setLayoutX(x);
		this.setLayoutY(y);
	}
	
	//宽度
	public DoubleProperty widthProperty();
	
	default void setWidth(double width) {
		this.widthProperty().set(width);
	}
	
	default double getWidth() {
		return this.widthProperty().get();
	}
	
	//高度
	public DoubleProperty heightProperty();
	
	default void setHeight(double height) {
		this.heightProperty().set(height);
	}
	
	default double getHeight() {
		return this.heightProperty().get();
	}
	
	//设置大小
	default void setSize(double width,double height) {
		this.setWidth(width);
		this.setHeight(height);
	}
	
	//x缩放
	public DoubleProperty scaleXProperty();
	
	default void setScaleX(double sx) {
		this.scaleXProperty().set(sx);
	}
	
	default double getScaleX() {
		return this.scaleXProperty().get();
	}
	
	//y缩放
	public DoubleProperty scaleYProperty();
	
	default void setScaleY(double sy) {
		this.scaleYProperty().set(sy);
	}
	
	default double getScaleY() {
		return this.scaleYProperty().get();
	}
	
	//整体缩放
	default void setScale(double sx,double sy) {
		this.setScaleX(sx);
		this.setScaleY(sy);
	}
	
	//设置旋转角度
	public DoubleProperty rotateProperty();
	
	default void setRotate(double r) {
		this.rotateProperty().set(r);
	}
	
	default double getRotate() {
		return this.rotateProperty().get();
	}
	
}
