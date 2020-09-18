package com.tricolorfire.graphics.drawable;

import java.util.LinkedList;

import com.tricolorfire.graphics.drawable.interfaces.IBrushEmployer;
import com.tricolorfire.graphics.drawable.interfaces.IDrawable;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;

public class DrawableGroup extends Group implements IBrushEmployer,IDrawable {
	
	@Override
	public ObjectProperty<BrushParameters> brushParametersProperty() {
		
		return null;
	}
	
	@Override
	public DoubleProperty xProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DoubleProperty yProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DoubleProperty widthProperty() {
		return null;
	}

	@Override
	public DoubleProperty heightProperty() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Group getGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	//
	//
	//
	//

	private ObservableList<IDrawable> drawableList;
	private void initDrawableChildren(){
		ObservableList<Node> nodeList = super.getChildren();
		LinkedList<IDrawable> innerDrawableList = new LinkedList<>();
		drawableList = FXCollections.observableList(innerDrawableList);
		
		//添加监听器，将修改的信息同步到nodeList里面
		drawableList.addListener(new ListChangeListener<IDrawable>() {
			@Override
			public void onChanged(Change<? extends IDrawable> c) {
				//执行这个才能获取信息
				c.next();
				//绑定同步
				
				//
				
				
			}
			
		});
		
	}
	public ObservableList<IDrawable> getDrawableChildren() {
		return drawableList;
	}
	
	/**
	 * 该方法被废弃,调用该方法将返回null,如果需要获取子节点请调用 getDrawableChildren()
	 * @return null
	 */
	@Override
	@Deprecated
	public ObservableList<Node> getChildren() {
		return null;
	}
}
