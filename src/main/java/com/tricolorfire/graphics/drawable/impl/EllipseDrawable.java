package com.tricolorfire.graphics.drawable.impl;

import com.tricolorfire.graphics.drawable.interfaces.IShapeDrawable;
import com.tricolorfire.graphics.util.IPropertyPlan;
import com.tricolorfire.graphics.util.PlannedDoubleProperty;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.shape.Ellipse;

public class EllipseDrawable extends Ellipse implements IShapeDrawable{
	
	private DoubleProperty widthProperty;
	private DoubleProperty heightProperty;
	
	public EllipseDrawable() {
		this(0,0,0,0);
	}

	public EllipseDrawable(double radiusX, double radiusY) {
		this(radiusX,radiusY,radiusX, radiusY);
	}
	
	public EllipseDrawable(double centerX, double centerY, double radiusX, double radiusY) {
		super(centerX,centerY, radiusX, radiusY);
		initSizeProperty(radiusX,radiusY);
	}
	
	private void initSizeProperty(double radiusX, double radiusY) {
		
		//初始化长宽设置
		widthProperty = new PlannedDoubleProperty(EllipseDrawable.this,"width",radiusX*2,new IPropertyPlan<Number>() {
			@Override
			public <E extends Property<Number>> void plan(E property, Number oldValue, Number newValue) {
				if(!oldValue.equals(newValue)) {
					radiusXProperty().set((double)newValue/2);
					double cx = centerXProperty().get() ;
					centerXProperty().set(cx + ((double)newValue - (double)oldValue)/2);
				}
			}
		});
		
		radiusXProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				widthProperty.set((double)newValue*2);
			}
		});
		
		heightProperty = new PlannedDoubleProperty(EllipseDrawable.this,"height",radiusY*2,new IPropertyPlan<Number>() {
			@Override
			public <E extends Property<Number>> void plan(E property, Number oldValue, Number newValue) {
				if(!oldValue.equals(newValue)) {
					radiusYProperty().set((double)newValue/2);
					double cy = centerYProperty().get() ;
					centerYProperty().set(cy + ((double)newValue - (double)oldValue)/2);
				}
			}
		});
		
		radiusYProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				heightProperty.set((double)newValue*2);
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
	public Ellipse getNode() {
		return this;
	}

	@Override
	public EllipseDrawable copy() {
		EllipseDrawable drawable = new EllipseDrawable();
		this.loadBoundsInfoTo(drawable);
		this.loadGraphicsInfoTo(drawable);
		return drawable;
	}
}
