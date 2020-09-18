package com.tricolorfire.graphics.drawable;

import java.util.LinkedList;

import com.tricolorfire.graphics.drawable.BrushParameters.ChangeEvent;
import com.tricolorfire.graphics.drawable.BrushParameters.IChangeListener;
import com.tricolorfire.graphics.drawable.interfaces.IBrushEmployer;
import com.tricolorfire.graphics.drawable.interfaces.IDrawable;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;

/**
 * 
 * 
 */
public class DrawableGroup extends Group implements IBrushEmployer,IDrawable {
	
	private static final String BRUSH_PARAMETERS = "brush parameters";
	
	private ObjectProperty<BrushParameters> brushParametersProperty;//笔刷参数
	private DoubleProperty widthProperty;
	private DoubleProperty heightProperty;
	
	public DrawableGroup() {
		init();
	}
	
	//初始化操作
	private void init() {
		brushParametersProperty = new SimpleObjectProperty<>(this,BRUSH_PARAMETERS,new BrushParameters());
		//设置监听器
		brushParametersProperty.get().addChangeListener(new IChangeListener() {
			//如果笔刷的一部分数据发生了改变，那么就让该组内的数据一起改变
			@Override
			public void onChanged(ChangeEvent event) {
				if(event.getNewValue() != null) {
					
				}
			}
		});
		//初始化DrawableChildren
		initDrawableChildren();
	}
	
	@Override
	public ObjectProperty<BrushParameters> brushParametersProperty() {
		return brushParametersProperty;
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
	public Group getNode() {
		return this;
	}

	/****************************************************************
	 *                       [ 跟随处理 ]                           * 
	 ****************************************************************/

	private ObservableList<IDrawable> drawableList;
	private void initDrawableChildren(){
		ObservableList<Node> nodeList = super.getChildren();
		LinkedList<IDrawable> innerDrawableList = new LinkedList<>();
		drawableList = FXCollections.observableList(innerDrawableList);
		
		//添加监听器，将修改的信息同步到nodeList里面
		drawableList.addListener(new ListChangeListener<IDrawable>() {
			@Override
			public void onChanged(Change<? extends IDrawable> c) {
				//绑定同步
				while (c.next()) {
					
				
				
				}
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

	@Override
	public DrawableType getType() {
		return DrawableType.GROUP;
	}
	
}
