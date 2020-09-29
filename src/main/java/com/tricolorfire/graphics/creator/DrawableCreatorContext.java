package com.tricolorfire.graphics.creator;

import java.util.LinkedList;

import com.tricolorfire.graphics.drawable.BrushParameters;
import com.tricolorfire.graphics.layer.Layer;
import com.tricolorfire.graphics.util.ObservableStack;
import com.tricolorfire.graphics.util.SimpleObservableStack;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 * DrawableCreatorContext
 * Drawable 构造器上下文
 * <br/>主要作用是在Drawable构造过程中提供相应的构造信息
 */
public class DrawableCreatorContext {
	
	private BrushParameters brushParameters; //画笔参数
	
	private ObservableList<Node> tmpNodes;   //临时层的节点
	
	//当鼠标左键松开(release)一次/检测到一次输入信号时，将鼠标坐标压入栈中(x坐标栈,y坐标栈)。
	private ObservableStack<Double> xPoints,yPoints; 
	
	private double nowX,nowY;                //鼠标现在的位置
	
	public DrawableCreatorContext(Layer layer) {
		tmpNodes = layer.getChildren();
		FXCollections.observableList(new LinkedList<Double>());
		xPoints = new SimpleObservableStack<>();
		yPoints = new SimpleObservableStack<>();
	}
	
	public double getNowX() {
		return nowX;
	}
	public void setNowX(double nowX) {
		this.nowX = nowX;
	}
	
	public double getNowY() {
		return nowY;
	}
	public void setNowY(double nowY) {
		this.nowY = nowY;
	}
	
	public ObservableList<Node> getTmpNodes() {
		return tmpNodes;
	}
	
	//获取第一个临时节点
	public Node fristTempNode() {
		if(tmpNodes.isEmpty()) return null;
		return tmpNodes.get(0);
	}
	
	//获取最后一个临时节点
	public Node lastTempNode() {
		if(tmpNodes.isEmpty()) return null;
		return tmpNodes.get(tmpNodes.size() -1);
	}
	
	public ObservableStack<Double> xPoints() {
		return xPoints;
	}
	public ObservableStack<Double> yPoints() {
		return yPoints;
	}
	
	public final BrushParameters getBrushParameters() {
		return brushParameters;
	}
	
}
