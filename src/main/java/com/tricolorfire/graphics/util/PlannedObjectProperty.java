package com.tricolorfire.graphics.util;

import javafx.beans.property.ObjectPropertyBase;

public class PlannedObjectProperty<T> extends ObjectPropertyBase<T>{

	private Object bean;
	private String name;
	private IPropertyPlan<T> plan;
	
	public PlannedObjectProperty(IPropertyPlan<T> plan) {
		this(null,plan);
	}
	
	public PlannedObjectProperty(T value , IPropertyPlan<T> plan) {
		this(null,"",value,plan);
	}
	
	public PlannedObjectProperty(Object bean, String name ,IPropertyPlan<T> plan) {
		this(bean,"",null,plan);
	}
	
	public PlannedObjectProperty(Object bean, String name , T value ,IPropertyPlan<T> plan) {
		super();
		this.plan = plan;
		this.bean = bean;
		this.name = name;
		this.set(value);
	}
	
    protected void invalidated() {
    	super.invalidated();
    	plan.plan(this);
    }
	
	@Override
	public Object getBean() {
		return bean;
	}

	@Override
	public String getName() {
		return name;
	}

}
