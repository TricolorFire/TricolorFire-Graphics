package com.tricolorfire.graphics.anchor;

import java.util.ArrayList;
import java.util.List;

import com.tricolorfire.graphics.drawable.interfaces.IBounds;
import com.tricolorfire.graphics.ui.PenetrablePane;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

public class RectangularDrawableControlPane extends PenetrablePane {
	
	//主要控制区域
	private IBounds contralBounds;
	
	//旋转节点配置
	private Anchor rotateAnchor;
	
	//拉伸节点
	private List<Anchor> anchors;
	
	//锚点方面设置
	private DoubleProperty anchorSizeProperty;
	private DoubleProperty anchorStrokeWidthProperty;
	
	//边框方面的设置
	private DoubleProperty borderStrokeWidthProperty;
	private ObjectProperty<Paint> borderStrokeFillProperty;
	
	private void init() {
		//contralBounds = new Region();
		
		//updateBind();
		anchors = new ArrayList<Anchor>();
		
		ChangeListener<? super Number> topYListener = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				((DoublePropertyBase)observable).set(0); //消除抖动
			}
		};

		ChangeListener<? super Number> leftXListener = topYListener;
		
		double width = 0;//adjustableRegion.widthProperty().doubleValue();
		double height = 0;//adjustableRegion.heightProperty().doubleValue();
		
		//initanchorSizeProperty();
		//initanchorStrokeWidthProperty();
		
		//上
		Anchor top = new Anchor(AnchorDirection.TOP,contralBounds);
		top.setLocation(width/2 , 0);
		top.sizeProperty().bind(anchorSizeProperty);
		top.layoutXProperty().bind(
				this.widthProperty().divide(2));
		top.layoutYProperty().addListener(topYListener);
		top.getShape().strokeWidthProperty().bind(anchorStrokeWidthProperty);
		
		//下
		Anchor bottom = new Anchor(AnchorDirection.BOTTOM,contralBounds);
		bottom.setLocation( width/2 , height );
		bottom.sizeProperty().bind(anchorSizeProperty);
		bottom.layoutXProperty().bind(
				this.widthProperty().divide(2));
		bottom.layoutYProperty().bindBidirectional(
				this.heightProperty());
		bottom.getShape().strokeWidthProperty().bind(anchorStrokeWidthProperty);
		
		//左
		Anchor left = new Anchor(AnchorDirection.LEFT,contralBounds);
		left.setLocation( 0 , height/2);
		left.sizeProperty().bind(anchorSizeProperty);
		left.layoutYProperty().bind(
				this.heightProperty().divide(2));
		left.layoutXProperty().addListener(leftXListener);
		left.getShape().strokeWidthProperty().bind(anchorStrokeWidthProperty);
		
		//右
		Anchor right = new Anchor(AnchorDirection.RIGHT,contralBounds);
		right.setLocation( width , height/2 );
		right.sizeProperty().bind(anchorSizeProperty);
		right.layoutXProperty().bindBidirectional(
				this.widthProperty());
		right.layoutYProperty().bind(
				this.heightProperty().divide(2));
		right.getShape().strokeWidthProperty().bind(anchorStrokeWidthProperty);
		
		//左上
		Anchor leftTop = new Anchor(AnchorDirection.LEFT_TOP,contralBounds);
		leftTop.setLocation( 0 , 0 );
		leftTop.sizeProperty().bind(anchorSizeProperty);
		leftTop.layoutXProperty().addListener(leftXListener);
		leftTop.layoutYProperty().addListener(topYListener);
		leftTop.getShape().strokeWidthProperty().bind(anchorStrokeWidthProperty);
		
		//左下
		Anchor leftBottom = new Anchor(AnchorDirection.LEFT_BOTTOM,contralBounds);
		leftBottom.setLocation( 0 , height );
		leftBottom.sizeProperty().bind(anchorSizeProperty);
		leftBottom.layoutXProperty().addListener(leftXListener);
		leftBottom.layoutYProperty().bindBidirectional(
				this.heightProperty());
		leftBottom.getShape().strokeWidthProperty().bind(anchorStrokeWidthProperty);
		
		//右上
		Anchor rightTop = new Anchor(AnchorDirection.RIGHT_TOP,contralBounds);
		rightTop.setLocation( width , 0 );
		rightTop.sizeProperty().bind(anchorSizeProperty);
		rightTop.layoutXProperty().bindBidirectional(
				this.widthProperty());
		rightTop.layoutYProperty().addListener(topYListener);
		rightTop.getShape().strokeWidthProperty().bind(anchorStrokeWidthProperty);
		
		//右下
		Anchor rightBottom = new Anchor(AnchorDirection.RIGHT_BOTTOM,contralBounds);
		rightBottom.setLocation( width , height );
		rightBottom.sizeProperty().bind(anchorSizeProperty);
		rightBottom.layoutXProperty().bindBidirectional(
				this.widthProperty());
		rightBottom.layoutYProperty().bindBidirectional(
				this.heightProperty());
		rightBottom.getShape().strokeWidthProperty().bind(anchorStrokeWidthProperty);
		
		///////////////
		//关于连接线的设置//
		///////////////
		//initBorderStrokeWidthProperty();
		//initBorderStrokeFillProperty();
		
		Line leftLine = new Line();
		leftLine.strokeProperty().bind(borderStrokeFillProperty);
		leftLine.strokeWidthProperty().bind(borderStrokeWidthProperty);
		leftLine.startXProperty().bind(leftTop.layoutXProperty());
		leftLine.startYProperty().bind(leftTop.layoutYProperty());
		leftLine.endXProperty().bind(leftBottom.layoutXProperty());
		leftLine.endYProperty().bind(leftBottom.layoutYProperty());
		
		Line rightLine = new Line();
		rightLine.strokeProperty().bind(borderStrokeFillProperty);
		rightLine.strokeWidthProperty().bind(borderStrokeWidthProperty);
		rightLine.startXProperty().bind(rightTop.layoutXProperty());
		rightLine.startYProperty().bind(rightTop.layoutYProperty());
		rightLine.endXProperty().bind(rightBottom.layoutXProperty());
		rightLine.endYProperty().bind(rightBottom.layoutYProperty());
		
		Line topLine = new Line();
		topLine.strokeProperty().bind(borderStrokeFillProperty);
		topLine.strokeWidthProperty().bind(borderStrokeWidthProperty);
		topLine.startXProperty().bind(leftTop.layoutXProperty());
		topLine.startYProperty().bind(leftTop.layoutYProperty());
		topLine.endXProperty().bind(rightTop.layoutXProperty());
		topLine.endYProperty().bind(rightTop.layoutYProperty());
		
		Line bottomLine = new Line();
		bottomLine.strokeProperty().bind(borderStrokeFillProperty);
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
		
		
		///////////////
		//关于旋转点的设置//
		///////////////
/*		
		//旋转点
		rotatePoint = new Anchor(AnchorDirection.ROTATE);
		rotatePoint.sizeProperty().bind(anchorSizeProperty);
		rotatePoint.layoutXProperty().bind(this.prefWidthProperty().divide(2));
		this.prefHeightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				double height = (double) newValue;
				double distence = Math.abs(rotatePoint.layoutYProperty().get());
				if(height < 0) {
					rotatePoint.layoutYProperty().set(distence);
				} else {
					rotatePoint.layoutYProperty().set(-distence);
				}
			}
		});
		rotatePoint.layoutYProperty().set(-ROTATE_POINT_DISTENCE);
		rotatePoint.getShape().setFill(Color.YELLOW);
		rotatePoint.getShape().strokeWidthProperty().bind(anchorStrokeWidthProperty);
		
		//初始化监听器
		initRotateListener();
		rotatePoint.getShape().addEventFilter(MouseEvent.ANY,rotateListener);
		
		Line rotateLine = new Line();
		rotateLine.strokeProperty().bind(borderStrokeFillProperty);
		rotateLine.strokeWidthProperty().bind(borderStrokeWidthProperty);
		
		rotateLine.startXProperty().bind(top.layoutXProperty());
		rotateLine.startYProperty().bind(top.layoutYProperty());	
		rotateLine.endXProperty().bind(rotatePoint.layoutXProperty());
		rotateLine.endYProperty().bind(rotatePoint.layoutYProperty());
		
		//左下虚拟点
		Anchor virtualPointLB = new Anchor(AnchorDirection.LEFT_BOTTOM);
		virtualPointLB.sizeProperty().bind(anchorSizeProperty);
		virtualPointLB.layoutXProperty().bind(leftBottom.layoutXProperty());
		virtualPointLB.layoutYProperty().bind(
				leftBottom.layoutYProperty().add(rotatePoint.layoutYProperty().multiply(-1)));
		virtualPointLB.getShape().setOpacity(0d);
		virtualPointLB.getShape().setMouseTransparent(true);
		virtualPointLB.getShape().strokeWidthProperty().bind(anchorStrokeWidthProperty);
		
		//右下虚拟点
		Anchor virtualPointRB = new Anchor(AnchorDirection.RIGHT_BOTTOM);
		virtualPointRB.sizeProperty().bind(anchorSizeProperty);
		virtualPointRB.layoutXProperty().bind(rightBottom.layoutXProperty());
		virtualPointRB.layoutYProperty().bind(
				rightBottom.layoutYProperty().add(rotatePoint.layoutYProperty().multiply(-1)));
		virtualPointRB.getShape().setOpacity(0d);
		virtualPointRB.getShape().setMouseTransparent(true);
		virtualPointRB.getShape().strokeWidthProperty().bind(anchorStrokeWidthProperty);
		
		//旋转点面板
		rotatePointPane = new PenetrablePane();
		
		//位置绑定
		rotatePointPane.layoutXProperty().bind(this.layoutXProperty());
		rotatePointPane.layoutYProperty().bind(this.layoutYProperty());
		
		//位移绑定
		rotatePointPane.translateXProperty().bindBidirectional(this.translateXProperty());
		rotatePointPane.translateYProperty().bindBidirectional(this.translateYProperty());
		
		//缩放绑定
		rotatePointPane.scaleXProperty().bindBidirectional(this.scaleXProperty());
		rotatePointPane.scaleYProperty().bindBidirectional(this.scaleYProperty());
		
		//旋转绑定
		rotatePointPane.rotateProperty().bind(this.rotateProperty());
		
		rotatePointPane.getChildren().addAll(
				rotateLine,
				
				virtualPointLB.getShape(),
				virtualPointRB.getShape(),
				rotatePoint.getShape());
		
		///////////////
		//对图形添加监听器//
		///////////////
		addShapeAwakenListener();
		addShapeMoveListener();
		
		//默认为不可视
		setVisible(false);
		rotatePointPane.setVisible(false);
*/
	}
	
}
