package com.tricolorfire.graphics.drawable.interfaces;

import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import javafx.scene.transform.Transform;

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
	
	//x平移
	public DoubleProperty translateXProperty();
	
	default void setTranslateX(double tx) {
		translateXProperty().set(tx);
	}
	
	default double getTranslateX() {
		return translateXProperty().get();
	}
	
	//y平移
	public DoubleProperty translateYProperty();
	
	default void setTranslateY(double ty) {
		translateYProperty().set(ty);
	}
	
	default double getTranslateY() {
		return translateYProperty().get();
	}
	
	//变换
	public ObservableList<Transform> getTransforms();
	
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
	
	//绑定
	default void bindBounds(IBounds bounds){
		this.layoutXProperty().bind(bounds.layoutXProperty());
		this.layoutYProperty().bind(bounds.layoutYProperty());
		this.widthProperty().bind(bounds.widthProperty());
		this.heightProperty().bind(bounds.heightProperty());
		this.translateXProperty().bind(bounds.translateXProperty());
		this.translateYProperty().bind(bounds.translateYProperty());
		this.scaleXProperty().bind(bounds.scaleXProperty());
		this.scaleYProperty().bind(bounds.scaleYProperty());
		this.rotateProperty().bind(bounds.rotateProperty());
	}
	
	//解绑
	default void unbindBounds(){
		this.layoutXProperty().unbind();
		this.layoutYProperty().unbind();
		this.widthProperty().unbind();
		this.heightProperty().unbind();
		this.translateXProperty().unbind();
		this.translateYProperty().unbind();
		this.scaleXProperty().unbind();
		this.scaleYProperty().unbind();
		this.rotateProperty().unbind();
	}
	
	//互相绑定
	default void bindBidirectionalBounds(IBounds bounds){
		this.layoutXProperty().bindBidirectional(bounds.layoutXProperty());
		this.layoutYProperty().bindBidirectional(bounds.layoutYProperty());
		this.widthProperty().bindBidirectional(bounds.widthProperty());
		this.heightProperty().bindBidirectional(bounds.heightProperty());
		this.translateXProperty().bindBidirectional(bounds.translateXProperty());
		this.translateYProperty().bindBidirectional(bounds.translateYProperty());
		this.scaleXProperty().bindBidirectional(bounds.scaleXProperty());
		this.scaleYProperty().bindBidirectional(bounds.scaleYProperty());
		this.rotateProperty().bindBidirectional(bounds.rotateProperty());
	}
	
	//解除互相绑定
	default void unbindBidirectionalBounds(IBounds bounds){
		this.layoutXProperty().unbindBidirectional(bounds.layoutXProperty());
		this.layoutYProperty().unbindBidirectional(bounds.layoutYProperty());
		this.widthProperty().unbindBidirectional(bounds.widthProperty());
		this.heightProperty().unbindBidirectional(bounds.heightProperty());
		this.translateXProperty().unbindBidirectional(bounds.translateXProperty());
		this.translateYProperty().unbindBidirectional(bounds.translateYProperty());
		this.scaleXProperty().unbindBidirectional(bounds.scaleXProperty());
		this.scaleYProperty().unbindBidirectional(bounds.scaleYProperty());
		this.rotateProperty().unbindBidirectional(bounds.rotateProperty());
	}
	
}
