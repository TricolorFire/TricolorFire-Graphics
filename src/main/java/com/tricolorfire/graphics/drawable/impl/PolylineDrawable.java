package com.tricolorfire.graphics.drawable.impl;

import java.util.List;

import com.tricolorfire.graphics.drawable.DrawableType;
import com.tricolorfire.graphics.drawable.interfaces.IDrawable;
import com.tricolorfire.graphics.util.IPropertyPlan;
import com.tricolorfire.graphics.util.PlannedDoubleProperty;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.scene.shape.Polyline;

public class PolylineDrawable extends Polyline implements IDrawable{
	
	private static final double[] EMPTY_DOUBLE_ARRAY = new double[] {};
	
	private DoubleProperty widthProperty;
	private DoubleProperty heightProperty;
	
	public PolylineDrawable() {		
		this(true,EMPTY_DOUBLE_ARRAY);
	}

	public PolylineDrawable(double... points) {
		this(true,points);
	}
	
	public PolylineDrawable(boolean flag ,double... points) {
		super(points);
		init(flag);
	}
	
	private void init(boolean flag) {
		
		//初始化widthProperty和heightProperty
		initSizeProperties(flag);
		
	}
	
	private double[] computeExtreme(List<Double> points) {
		boolean xFlag = true;
		double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE, maxX = Double.MIN_VALUE,maxY = Double.MIN_VALUE;
		for(Double point : points) {
			if(xFlag) {
				if(point > maxX) {
					maxX = point;
				}
				if(point < minX) {
					minX =point;
				}
			} else {
				if(point > maxY) {
					maxY = point;
				}
				if(point < minY) {
					minY =point;
				}
			}
			xFlag = !xFlag;
		}
		return new double[] {minX,minY,maxX,maxY};
	}
	
	private void moveTo(List<Double> points,double x,double y) {
		boolean xFlag = true;
		for(int i = 0 ; i < points.size() ;i ++) {
			if(xFlag) {
				points.set(i, points.get(i) + x);
			} else {
				points.set(i, points.get(i) + y);
			}
		}
	}
	
	private void initSizeProperties(boolean flag) {
		
		double[] extreme = computeExtreme(getPoints());
		double width = extreme[2] - extreme[0];
		double height = extreme[3] - extreme[1];
		
		if(flag) {
			//让其移动到(0,0)位置
			moveTo(getPoints(), -extreme[0], -extreme[1]);
		}
		
		widthProperty = new PlannedDoubleProperty(PolylineDrawable.this, "width", width, new IPropertyPlan<Number>() {
			@Override
			public <E extends Property<Number>> void plan(E property, Number oldValue, Number newValue) {
				if(oldValue.equals(newValue)) return;
				ObservableList<Double> points = getPoints();
				double scale = (double)newValue/(double)oldValue;
				double size = points.size()/2;
				
				for(int i = 0 ; i < size ; i++) {
					points.set(i*2,points.get(i*2)*scale);
				}
			}
		});
		
		heightProperty = new PlannedDoubleProperty(PolylineDrawable.this, "height", height, new IPropertyPlan<Number>() {
			@Override
			public <E extends Property<Number>> void plan(E property, Number oldValue, Number newValue) {
				if(oldValue.equals(newValue)) return;
				ObservableList<Double> points = getPoints();
				double scale = (double)newValue/(double)oldValue;
				double size = points.size()/2;
				for(int i = 0 ; i < size ; i++) {
					points.set(i*2 + 1,points.get(i*2 + 1)*scale);
				}
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
	public Polyline getNode() {
		return this;
	}

	@Override
	public DrawableType getType() {
		return DrawableType.SHAPE;
	}

}
