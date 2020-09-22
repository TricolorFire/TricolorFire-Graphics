package com.tricolorfire.graphics.layer;

import com.tricolorfire.graphics.ui.PenetrablePane;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class LayerPane extends Pane {
	
	private PenetrablePane operationLayer;          //操作层
	private Layer          temporaryDrawingLayer;   //临时绘制层
	private Layer          vectorLayer;      		//矢量层
	
	public LayerPane() {
		
		operationLayer = new PenetrablePane();
		temporaryDrawingLayer = new Layer();
		vectorLayer = new Layer();
		
		//将这些层添加入面板中
		super.getChildren().addAll(vectorLayer,temporaryDrawingLayer,operationLayer);
	}
	
	public PenetrablePane getOperationLayer() {
		return operationLayer;
	}

	public Layer getTemporaryDrawingLayer() {
		return temporaryDrawingLayer;
	}

	public Layer getVectorLayer() {
		return vectorLayer;
	}

	@Deprecated
	public ObservableList<Node> getChildren() {
		return super.getChildrenUnmodifiable();
	}
	
}
