package com.tricolorfire.graphics.ui;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;

/**
 * PenetrablePane
 * 可穿透面板 : 鼠标监听可以穿透本面板，但本面板的组件依然可以监听鼠标行为
 */
public class PenetrablePane extends Parent{
	
	public PenetrablePane() {
		super();
	}

	//将获取子节点的方法设为公开
	@Override
	public ObservableList<Node> getChildren() {
		return super.getChildren();
	}
	
}
