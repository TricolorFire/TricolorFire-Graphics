package com.tricolorfire.graphics.drawable;

import com.tricolorfire.graphics.drawable.impl.RectangleDrawable;
import com.tricolorfire.graphics.drawable.interfaces.IGraphics;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;

public class BrushParameters implements IGraphics {

	private ObjectProperty<Paint> fillProperty = new SimpleObjectProperty<>();
	private ObjectProperty<Paint> strokeProperty = new SimpleObjectProperty<>();
	private DoubleProperty strokeWidthProperty = new SimpleDoubleProperty();
	private ObjectProperty<StrokeType> strokeTypeProperty = new SimpleObjectProperty<>();
	private DoubleProperty strokeMiterLimitProperty = new SimpleDoubleProperty();
	private ObjectProperty<StrokeLineJoin> strokeLineJoinProperty = new SimpleObjectProperty<>();
	private ObjectProperty<StrokeLineCap> strokeLineCapProperty = new SimpleObjectProperty<>();
	private DoubleProperty strokeDashOffsetProperty = new SimpleDoubleProperty();
	
	
	public BrushParameters() {
		init();
	}
	
	private void init() {
		RectangleDrawable rect = new RectangleDrawable();
		rect.loadGraphicsInfoTo(this);
	}
	
	@Override
	public ObjectProperty<Paint> fillProperty() {
		return fillProperty;
	}

	@Override
	public ObjectProperty<Paint> strokeProperty() {
		return strokeProperty;
	}

	@Override
	public DoubleProperty strokeWidthProperty() {
		return strokeWidthProperty;
	}

	@Override
	public ObjectProperty<StrokeType> strokeTypeProperty() {
		return strokeTypeProperty;
	}

	@Override
	public DoubleProperty strokeMiterLimitProperty() {
		return strokeMiterLimitProperty;
	}

	@Override
	public ObjectProperty<StrokeLineJoin> strokeLineJoinProperty() {
		return strokeLineJoinProperty;
	}

	@Override
	public ObjectProperty<StrokeLineCap> strokeLineCapProperty() {
		return strokeLineCapProperty;
	}

	@Override
	public DoubleProperty strokeDashOffsetProperty() {
		return strokeDashOffsetProperty;
	}
	
}
