package com.tricolorfire.graphics.drawable.impl;

import com.tricolorfire.graphics.drawable.DrawableType;
import com.tricolorfire.graphics.drawable.interfaces.IDrawable;
import com.tricolorfire.graphics.util.IPropertyPlan;
import com.tricolorfire.graphics.util.PlannedDoubleProperty;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class CircleDrawable extends Circle implements IDrawable{
	
	public static final Paint DEFAULT_FILL = Color.BLACK;
	
	private DoubleProperty widthProperty;
	private DoubleProperty heightProperty;
	
	public CircleDrawable() {
		super();
	}

	public CircleDrawable(double centerX, double centerY, double radius, Paint fill) {
		super(centerX, centerY, radius, fill);
		init(radius);
	}

	public CircleDrawable(double centerX, double centerY, double radius) {
		this(centerX, centerY, radius,DEFAULT_FILL);
	}

	public CircleDrawable(double radius, Paint fill) {
		this(0,0,radius, fill);
	}

	public CircleDrawable(double radius) {
		this(radius,DEFAULT_FILL);
	}

	private void init(double radius) {
		//初始化长宽设置
		widthProperty = new PlannedDoubleProperty(CircleDrawable.this,"width",radius*2,new IPropertyPlan<Number>() {
			@Override
			public <E extends Property<Number>> void plan(E property, Number oldValue, Number newValue) {
				if(!oldValue.equals(newValue)) {
					radiusProperty().set((double)newValue/2);
				}
			}
		});
		heightProperty = new SimpleDoubleProperty();
		widthProperty.bindBidirectional(heightProperty);
		
		radiusProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				widthProperty.set((double)newValue*2);
			}
		});
		
	}
	
	@Override
	public DoubleProperty widthProperty() {	
		return widthProperty;
	}

	@Override
	public DoubleProperty heightProperty() {
		return heightProperty;
	}

	@Override
	public Node getNode() {
		return this;
	}

	@Override
	public DrawableType getType() {
		return DrawableType.SHAPE;
	}

}
