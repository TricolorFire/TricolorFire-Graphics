package com.tricolorfire.graphics.util;

import javafx.beans.property.SimpleDoubleProperty;

public class PlannedDoubleProperty extends SimpleDoubleProperty {
	
	private IPropertyPlan<Number> plan;

	public PlannedDoubleProperty(IPropertyPlan<Number> plan) {
		this(0d,plan);
	}

	public PlannedDoubleProperty(double initialValue,IPropertyPlan<Number> plan) {
		this(null,"",0d,plan);
	}
	
	public PlannedDoubleProperty(Object bean, String name,IPropertyPlan<Number> plan) {
		this(bean,name,0d,plan);
	}
	
	public PlannedDoubleProperty(Object bean, String name, double initialValue,IPropertyPlan<Number> plan) {
		super(bean, name, initialValue);
		this.plan = plan;
		this.oldValue = getValue();
	}
	
	private Number oldValue;
    protected void invalidated() {
    	super.invalidated();
    	Number newValue = getValue();
    	plan.plan(this,oldValue,newValue);
    	oldValue = newValue;
    }

}
