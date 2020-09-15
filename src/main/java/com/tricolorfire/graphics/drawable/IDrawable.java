package com.tricolorfire.graphics.drawable;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Group;
import javafx.scene.paint.Paint;

public interface IDrawable extends IBounds {
	
	//设置填充
	public ObjectProperty<Paint> fillProperty();
	
	default void setFill(Paint paint) {
		this.fillProperty().set(paint);
	}
	
	default Paint getFill() {
		return this.fillProperty().get();
	}
	
	//设置
	
	//获得一个Group
	public Group getGroup();
}
