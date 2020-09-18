package com.tricolorfire.graphics.util;

import javafx.beans.property.Property;

public interface IPropertyPlan<T> {
	public <E extends Property<T>> void plan(E property,T oldValue,T newValue);
}
