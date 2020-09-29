package com.tricolorfire.graphics.layer;

import java.util.ArrayList;
import java.util.List;

import com.tricolorfire.graphics.drawable.interfaces.IDrawable;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class Layer extends Pane implements ILayer {

	@Override
	public void addDrawable(IDrawable drawable) {
		super.getChildren().add(drawable.getNode());
	}

	@Override
	public void addAllDrawable(IDrawable... drawables) {
		for(IDrawable drawable : drawables) {
			addDrawable(drawable);
		}
	}

	@Override
	public void removeDrawable(IDrawable drawable) {
		super.getChildren().remove(drawable.getNode());
	}

	@Override
	public void clearDrawable() {
		super.getChildren().clear();
	}
	
	/**
	 *   该方法不可用
	 *   会返回一个不可修改的列表
	 */
	@Deprecated
	@Override
	public ObservableList<Node> getChildren() {
		return super.getChildrenUnmodifiable();
	}

}
