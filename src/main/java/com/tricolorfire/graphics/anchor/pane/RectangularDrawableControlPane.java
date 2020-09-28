package com.tricolorfire.graphics.anchor.pane;

import java.util.ArrayList;
import java.util.List;

import com.tricolorfire.graphics.anchor.Anchor;
import com.tricolorfire.graphics.anchor.AnchorDirection;
import com.tricolorfire.graphics.anchor.interfaces.IScaleAdapter;
import com.tricolorfire.graphics.drawable.interfaces.IBounds;
import com.tricolorfire.graphics.ui.PenetrablePane;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

public class RectangularDrawableControlPane extends PenetrablePane implements IScaleAdapter{
	
	public static final double ROTATE_POINT_DISTENCE = 30;
	
	public static final double DEFAULT_BORDER_STROKE_WIDTH = 1.0;
	public static final Paint DEFAULT_BORDER_STROKE = Color.BLUE;
	
	public static final double DEFAULT_ANCHOR_SIZE = 8.0; 
	public static final double DEFAULT_ANCHOR_STRPKE_WIDTH = 1.0;
	
	//主要控制区域
	private IBounds controlBounds;
	
	//拉伸节点
	private List<Anchor> anchors;
	
	//锚点方面设置
	private DoubleProperty anchorSizeProperty = 
			new SimpleDoubleProperty(this, "anchor size",DEFAULT_ANCHOR_SIZE);
	private DoubleProperty anchorStrokeWidthProperty = 
			new SimpleDoubleProperty(this, "anchor stroke width",DEFAULT_ANCHOR_STRPKE_WIDTH);
	
	//边框方面的设置
	private DoubleProperty borderStrokeWidthProperty =
			new SimpleDoubleProperty(this, "border stroke width",DEFAULT_BORDER_STROKE_WIDTH);
	private ObjectProperty<Paint> borderStrokeProperty = 
			new SimpleObjectProperty<Paint>(this, "border stroke",DEFAULT_BORDER_STROKE);
	
	public RectangularDrawableControlPane(IBounds contralBounds) {
		this.controlBounds = contralBounds;
		init();
	}
	
	private void init() {
		
		this.bindBidirectionalBounds(controlBounds);

		anchors = new ArrayList<Anchor>();
		
		ChangeListener<? super Number> topYListener = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				//删掉似乎也没影响
				((DoublePropertyBase)observable).set(0); //消除抖动
			}
		};

		ChangeListener<? super Number> leftXListener = topYListener;
		
		double width = controlBounds.getWidth();
		double height = controlBounds.getHeight();
		
		//上
		Anchor top = new Anchor(AnchorDirection.TOP,controlBounds);
		top.setLocation(width/2 , 0);
		top.sizeProperty().bind(anchorSizeProperty);
		top.layoutXProperty().bind(
				this.widthProperty().divide(2));
		top.layoutYProperty().addListener(topYListener);
		
		top.getShape().strokeWidthProperty().bind(anchorStrokeWidthProperty);
		
		//下
		Anchor bottom = new Anchor(AnchorDirection.BOTTOM,controlBounds);
		bottom.setLocation( width/2 , height );
		bottom.sizeProperty().bind(anchorSizeProperty);
		bottom.layoutXProperty().bind(
				this.widthProperty().divide(2));
		bottom.layoutYProperty().bindBidirectional(
				this.heightProperty());
		bottom.getShape().strokeWidthProperty().bind(anchorStrokeWidthProperty);
		
		//左
		Anchor left = new Anchor(AnchorDirection.LEFT,controlBounds);
		left.setLocation( 0 , height/2);
		left.sizeProperty().bind(anchorSizeProperty);
		left.layoutYProperty().bind(
				this.heightProperty().divide(2));
		left.layoutXProperty().addListener(leftXListener);
		left.getShape().strokeWidthProperty().bind(anchorStrokeWidthProperty);
		
		//右
		Anchor right = new Anchor(AnchorDirection.RIGHT,controlBounds);
		right.setLocation( width , height/2 );
		right.sizeProperty().bind(anchorSizeProperty);
		right.layoutXProperty().bindBidirectional(
				this.widthProperty());
		right.layoutYProperty().bind(
				this.heightProperty().divide(2));
		right.getShape().strokeWidthProperty().bind(anchorStrokeWidthProperty);
		
		//左上
		Anchor leftTop = new Anchor(AnchorDirection.LEFT_TOP,controlBounds);
		leftTop.setLocation( 0 , 0 );
		leftTop.sizeProperty().bind(anchorSizeProperty);
		leftTop.layoutXProperty().addListener(leftXListener);
		leftTop.layoutYProperty().addListener(topYListener);
		leftTop.getShape().strokeWidthProperty().bind(anchorStrokeWidthProperty);
		
		//左下
		Anchor leftBottom = new Anchor(AnchorDirection.LEFT_BOTTOM,controlBounds);
		leftBottom.setLocation( 0 , height );
		leftBottom.sizeProperty().bind(anchorSizeProperty);
		leftBottom.layoutXProperty().addListener(leftXListener);
		leftBottom.layoutYProperty().bindBidirectional(
				this.heightProperty());
		leftBottom.getShape().strokeWidthProperty().bind(anchorStrokeWidthProperty);
		
		//右上
		Anchor rightTop = new Anchor(AnchorDirection.RIGHT_TOP,controlBounds);
		rightTop.setLocation( width , 0 );
		rightTop.sizeProperty().bind(anchorSizeProperty);
		rightTop.layoutXProperty().bindBidirectional(
				this.widthProperty());
		rightTop.layoutYProperty().addListener(topYListener);
		rightTop.getShape().strokeWidthProperty().bind(anchorStrokeWidthProperty);
		
		//右下
		Anchor rightBottom = new Anchor(AnchorDirection.RIGHT_BOTTOM,controlBounds);
		rightBottom.setLocation( width , height );
		rightBottom.sizeProperty().bind(anchorSizeProperty);
		rightBottom.layoutXProperty().bindBidirectional(
				this.widthProperty());
		rightBottom.layoutYProperty().bindBidirectional(
				this.heightProperty());
		rightBottom.getShape().strokeWidthProperty().bind(anchorStrokeWidthProperty);
		
		////////////////////
		//关于连接线的设置//
		////////////////////
		
		Line leftLine = new Line();
		leftLine.strokeProperty().bind(borderStrokeProperty);
		leftLine.strokeWidthProperty().bind(borderStrokeWidthProperty);
		leftLine.startXProperty().bind(leftTop.layoutXProperty());
		leftLine.startYProperty().bind(leftTop.layoutYProperty());
		leftLine.endXProperty().bind(leftBottom.layoutXProperty());
		leftLine.endYProperty().bind(leftBottom.layoutYProperty());
		
		Line rightLine = new Line();
		rightLine.strokeProperty().bind(borderStrokeProperty);
		rightLine.strokeWidthProperty().bind(borderStrokeWidthProperty);
		rightLine.startXProperty().bind(rightTop.layoutXProperty());
		rightLine.startYProperty().bind(rightTop.layoutYProperty());
		rightLine.endXProperty().bind(rightBottom.layoutXProperty());
		rightLine.endYProperty().bind(rightBottom.layoutYProperty());
		
		Line topLine = new Line();
		topLine.strokeProperty().bind(borderStrokeProperty);
		topLine.strokeWidthProperty().bind(borderStrokeWidthProperty);
		topLine.startXProperty().bind(leftTop.layoutXProperty());
		topLine.startYProperty().bind(leftTop.layoutYProperty());
		topLine.endXProperty().bind(rightTop.layoutXProperty());
		topLine.endYProperty().bind(rightTop.layoutYProperty());
		
		Line bottomLine = new Line();
		bottomLine.strokeProperty().bind(borderStrokeProperty);
		bottomLine.strokeWidthProperty().bind(borderStrokeWidthProperty);
		bottomLine.startXProperty().bind(leftBottom.layoutXProperty());
		bottomLine.startYProperty().bind(leftBottom.layoutYProperty());
		bottomLine.endXProperty().bind(rightBottom.layoutXProperty());
		bottomLine.endYProperty().bind(rightBottom.layoutYProperty());
		
		this.getChildren().addAll(
				leftLine,
				rightLine,
				topLine,
				bottomLine,

				leftTop.getShape(),
				leftBottom.getShape(),
				rightTop.getShape(),
				rightBottom.getShape(),
				
				left.getShape(),
				right.getShape(),
				top.getShape(),
				bottom.getShape()
				
				);
		
		anchors.add(top);
		anchors.add(bottom);
		anchors.add(left);
		anchors.add(right);
		anchors.add(leftTop);
		anchors.add(leftBottom);
		anchors.add(rightTop);
		anchors.add(rightBottom);
		
		////////////////////
		//对图形添加监听器//
		////////////////////
		//addShapeAwakenListener();
		//addShapeMoveListener();
		
		//默认为不可视
		//setVisible(false);
		
	}

	/********************************************************
	 *                                                      *
	 *                   自适应大小                    *
	 *                                                      *
	 ********************************************************/
	
	private ChangeListener<Number> scaleChangeListener ;
	private DoubleProperty scaleAdapter;
	
	//设置适配器
	public void adaptToNewScale(DoubleProperty scaleProperty) {
		if(scaleChangeListener == null) {
			scaleChangeListener = new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					updateScale();
				}
			};
		}

		if(this.scaleAdapter != null) {
			this.scaleAdapter.removeListener(scaleChangeListener);
		}
		
		this.scaleAdapter = scaleProperty;
		this.scaleAdapter.addListener(scaleChangeListener);
		
		updateScale();

	}
	
	//根据scale来自适应 锚点 及 边界线宽度
	private void updateScale() {
		
		//获取比例数据
		double scale = scaleAdapter.doubleValue();

		//边界宽度自适应
		double borderWidth = DEFAULT_BORDER_STROKE_WIDTH/scale;
		borderStrokeWidthProperty.set(borderWidth);
		
		//锚点大小自适应
		double size = DEFAULT_ANCHOR_SIZE/scale;
		this.anchorSizeProperty.set(size);
		this.anchorStrokeWidthProperty.set(1.0/scale);
		
	}
	
}
