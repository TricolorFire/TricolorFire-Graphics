package com.tricolorfire.graphics.creator;

import java.util.Deque;

import com.tricolorfire.graphics.drawable.BrushParameters;

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
	
	private Deque<Double> xPoints,yPoints;   //当鼠标左键松开(release)一次/检测到一次输入信号时
										     //将鼠标坐标压入栈中(x坐标栈,y坐标栈)。
	
	private double nowX,nowY;                //鼠标现在的位置
	
	
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
	
	public Deque<Double> getXPoints() {
		return xPoints;
	}
	public Deque<Double> getYPoints() {
		return yPoints;
	}
	
	public final BrushParameters getBrushParameters() {
		return brushParameters;
	}
	
}
