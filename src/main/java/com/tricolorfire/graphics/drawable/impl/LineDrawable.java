package com.tricolorfire.graphics.drawable.impl;

import com.tricolorfire.graphics.drawable.DrawableType;
import com.tricolorfire.graphics.drawable.interfaces.IDrawable;
import com.tricolorfire.graphics.util.IPropertyPlan;
import com.tricolorfire.graphics.util.PlannedDoubleProperty;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.shape.Line;

public class LineDrawable extends Line implements IDrawable{

	private DoubleProperty widthProperty;
	private DoubleProperty heightProperty;
	
	public LineDrawable() {
		this(0,0,0,0);
	}

	public LineDrawable(double startX, double startY, double endX, double endY) {
		super(startX, startY, endX, endY);
		init(startX, startY, endX, endY);
	}

	private void init(double startX,double startY,double endX,double endY) {
		
		double width = Math.abs(endX- startX);
		double height = Math.abs(endY - startY);
		
		widthProperty = new PlannedDoubleProperty(LineDrawable.this, "width", width, new IPropertyPlan<Number>() {
			@Override
			public <E extends Property<Number>> void plan(E property, Number oldValue, Number newValue) {
				if(oldValue.equals(newValue)) return;
				
				//判断谁在右下角，并获得右下角的配置器
				DoubleProperty rbProperty = null;
				
				if(getStartX() > getEndX()) {
					rbProperty = startXProperty();
				} else {
					rbProperty = endXProperty();
				}
				
				//调整下角的位置
				double dValue = (double)newValue - (double)oldValue;
				rbProperty.set(rbProperty.get() + dValue);
			}
		});
		heightProperty = new PlannedDoubleProperty(LineDrawable.this, "height", height, new IPropertyPlan<Number>() {
			@Override
			public <E extends Property<Number>> void plan(E property, Number oldValue, Number newValue) {
				if(oldValue.equals(newValue)) return;
				
				//判断谁在右下角，并获得右下角的配置器
				DoubleProperty rbProperty = null;
				
				if(getStartY() > getEndY()) {
					rbProperty = startYProperty();
				} else {
					rbProperty = endYProperty();
				}
				
				//调整下角的位置
				double dValue = (double)newValue - (double)oldValue;
				rbProperty.set(rbProperty.get() + dValue);
			}
		});
		
		/*
		 * 对startX endX startY endY 添加监听器 
		 */
		ChangeListener<Number> xChangeListener = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				widthProperty.set(Math.abs(getStartX() - getEndX() ));
			}
		};
		startXProperty().addListener(xChangeListener);
		endXProperty().addListener(xChangeListener);
		
		ChangeListener<Number> yChangeListener = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				widthProperty.set(Math.abs(getStartY() - getEndY() ));
			}
		};
		startYProperty().addListener(yChangeListener);
		endYProperty().addListener(yChangeListener);
		
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
	public Line getNode() {
		return this;
	}

	@Override
	public DrawableType getType() {
		return DrawableType.SHAPE;
	}

}
