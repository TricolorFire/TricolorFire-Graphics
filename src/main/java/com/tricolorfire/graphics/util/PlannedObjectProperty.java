package com.tricolorfire.graphics.util;

import javafx.beans.property.SimpleObjectProperty;

public class PlannedObjectProperty<T> extends SimpleObjectProperty<T>{

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
		super(bean,name,value);
		this.plan = plan;
		oldValue = getValue();
	}
	
	private T oldValue;
    protected void invalidated() {
    	super.invalidated();
    	T newValue = getValue();
    	plan.plan(this,oldValue,newValue);
    	oldValue = newValue;
    }
	
}
