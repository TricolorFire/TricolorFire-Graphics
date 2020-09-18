package com.tricolorfire.graphics.drawable.interfaces;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;

public interface IGraphics {
	
	//设置填充
	public ObjectProperty<Paint> fillProperty();
	
	default void setFill(Paint paint) {
		this.fillProperty().set(paint);
	}
	
	default Paint getFill() {
		return this.fillProperty().get();
	}
	
	//设置Stroke
	public ObjectProperty<Paint> strokeProperty();
	
	default void setStroke(Paint paint) {
		this.strokeProperty().set(paint);
	}
	
	default Paint getStroke() {
		return this.strokeProperty().get();
	}
	
	//设置Stroke宽
	public DoubleProperty strokeWidthProperty();
	
	default void setStrokeWidth(double width) {
		this.strokeWidthProperty().set(width);
	}
	
	default double getStrokeWidth() {
		return this.strokeWidthProperty().get();
	}
	
	//设置Stroke类型
	public ObjectProperty<StrokeType> strokeTypeProperty();
	
	default void setStrokeType(StrokeType type) {
		this.strokeTypeProperty().set(type);
	}
	
	default StrokeType getStrokeType() {
		return this.strokeTypeProperty().get();
	}
	
	//设置StrokeMiterLimit
	public DoubleProperty strokeMiterLimitProperty();
	
	default void setStrokeMiterLimit(double ml) {
		this.strokeMiterLimitProperty().set(ml);
	}
	
	default double getStrokeMiterLimit() {
		return this.strokeMiterLimitProperty().get();
	}
	
	//设置StrokeLineJoin
	public ObjectProperty<StrokeLineJoin> strokeLineJoinProperty();
	
	default void setStrokeLineJoin(StrokeLineJoin join) {
		this.strokeLineJoinProperty().set(join);
	}
	
	default StrokeLineJoin getStrokeLineJoin() {
		return this.strokeLineJoinProperty().get();
	}
	
	//设置StrokeLineCap
	public ObjectProperty<StrokeLineCap> strokeLineCapProperty();
	
	default void setStrokeLineCap(StrokeLineCap cap) {
		this.strokeLineCapProperty().set(cap);
	}
	
	default StrokeLineCap getStrokeLineCap() {
		return this.strokeLineCapProperty().get();
	}
	
	//设置StrokeDashOffset
	public DoubleProperty strokeDashOffsetProperty();
	
	default void setStrokeDashOffset(double n) {
		this.strokeDashOffsetProperty().set(n);
	}
	
	default double getStrokeDashOffset() {
		return this.strokeDashOffsetProperty().get();
	}
	
	//将属性赋值给其他graphics
	default void loadGraphicsInfoTo(IGraphics graphics) {
		graphics.setFill(this.getFill());
		graphics.setStroke(this.getStroke());
		graphics.setStrokeDashOffset(this.getStrokeDashOffset());
		graphics.setStrokeLineCap(this.getStrokeLineCap());
		graphics.setStrokeLineJoin(this.getStrokeLineJoin());
		graphics.setStrokeMiterLimit(this.getStrokeMiterLimit());
		graphics.setStrokeType(this.getStrokeType());
		graphics.setStrokeWidth(this.getStrokeWidth());
	}
	
	//绑定
	default void bindGraphics(IGraphics graphics) {
		this.fillProperty().bind(graphics.fillProperty());
		this.strokeProperty().bind(graphics.strokeProperty());
		this.strokeDashOffsetProperty().bind(graphics.strokeDashOffsetProperty());
		this.strokeLineCapProperty().bind(graphics.strokeLineCapProperty());
		this.strokeLineJoinProperty().bind(graphics.strokeLineJoinProperty());
		this.strokeMiterLimitProperty().bind(graphics.strokeMiterLimitProperty());
		this.strokeTypeProperty().bind(graphics.strokeTypeProperty());
		this.strokeWidthProperty().bind(graphics.strokeWidthProperty());
	}
	
	//解绑
	default void unbindGraphics() {
		this.fillProperty().unbind();
		this.strokeProperty().unbind();
		this.strokeDashOffsetProperty().unbind();
		this.strokeLineCapProperty().unbind();
		this.strokeLineJoinProperty().unbind();
		this.strokeMiterLimitProperty().unbind();
		this.strokeTypeProperty().unbind();
		this.strokeWidthProperty().unbind();
	}
	
	//互相绑定
	default void bindBidirectionalGraphics(IGraphics graphics) {
		this.fillProperty().bindBidirectional(graphics.fillProperty());
		this.strokeProperty().bindBidirectional(graphics.strokeProperty());
		this.strokeDashOffsetProperty().bindBidirectional(graphics.strokeDashOffsetProperty());
		this.strokeLineCapProperty().bindBidirectional(graphics.strokeLineCapProperty());
		this.strokeLineJoinProperty().bindBidirectional(graphics.strokeLineJoinProperty());
		this.strokeMiterLimitProperty().bindBidirectional(graphics.strokeMiterLimitProperty());
		this.strokeTypeProperty().bindBidirectional(graphics.strokeTypeProperty());
		this.strokeWidthProperty().bindBidirectional(graphics.strokeWidthProperty());
	}	
	//互相解绑
	default void unbindBidirectionalGraphics(IGraphics graphics) {
		this.fillProperty().unbindBidirectional(graphics.fillProperty());
		this.strokeProperty().unbindBidirectional(graphics.strokeProperty());
		this.strokeDashOffsetProperty().unbindBidirectional(graphics.strokeDashOffsetProperty());
		this.strokeLineCapProperty().unbindBidirectional(graphics.strokeLineCapProperty());
		this.strokeLineJoinProperty().unbindBidirectional(graphics.strokeLineJoinProperty());
		this.strokeMiterLimitProperty().unbindBidirectional(graphics.strokeMiterLimitProperty());
		this.strokeTypeProperty().unbindBidirectional(graphics.strokeTypeProperty());
		this.strokeWidthProperty().unbindBidirectional(graphics.strokeWidthProperty());
	}	
}
