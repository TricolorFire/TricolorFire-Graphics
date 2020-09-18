package com.tricolorfire.graphics.drawable;

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
	
}
