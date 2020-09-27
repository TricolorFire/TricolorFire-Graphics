package com.tricolorfire.graphics.anchor.pane;

import java.util.ArrayList;
import java.util.List;

import com.tricolorfire.graphics.anchor.Anchor;
import com.tricolorfire.graphics.anchor.AnchorDirection;
import com.tricolorfire.graphics.coordinate.CoordinateHelper;
import com.tricolorfire.graphics.drawable.interfaces.IBounds;
import com.tricolorfire.graphics.layer.LayerPane;
import com.tricolorfire.graphics.ui.PenetrablePane;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

public class RectangularDrawableControlPane extends PenetrablePane implements IBounds{
	
	public static final double ROTATE_POINT_DISTENCE = 30;
	
	public static final double DEFAULT_BORDER_STRPKE_WIDTH = 1.0;
	public static final Paint DEFAULT_BORDER_STRPKE = Color.BLUE;
	
	public static final double DEFAULT_ANCHOR_SIZE = 6.0; 
	public static final double DEFAULT_ANCHOR_STRPKE_WIDTH = 1.0;
	
	//主要控制区域
	private IBounds contralBounds;
	
	//旋转节点配置
	private Anchor rotateAnchor;
	private PenetrablePane rotateAnchorPane;
	private EventHandler<MouseEvent> rotateListener;
	
	//拉伸节点
	private List<Anchor> anchors;
	
	//锚点方面设置
	private DoubleProperty anchorSizeProperty = 
			new SimpleDoubleProperty(this, "anchor size",DEFAULT_ANCHOR_SIZE);
	private DoubleProperty anchorStrokeWidthProperty = 
			new SimpleDoubleProperty(this, "anchor stroke width",DEFAULT_ANCHOR_STRPKE_WIDTH);
	
	//边框方面的设置
	private DoubleProperty borderStrokeWidthProperty =
			new SimpleDoubleProperty(this, "border stroke width",DEFAULT_BORDER_STRPKE_WIDTH);
	private ObjectProperty<Paint> borderStrokeProperty = 
			new SimpleObjectProperty<Paint>(this, "border stroke",DEFAULT_BORDER_STRPKE);
	
	public RectangularDrawableControlPane(IBounds contralBounds) {
		this.contralBounds = contralBounds;
		init();
	}
	
	private void init() {
		
		this.bindBidirectionalBounds(contralBounds);

		anchors = new ArrayList<Anchor>();
		
		ChangeListener<? super Number> topYListener = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				//TODO 删掉似乎也没影响
				((DoublePropertyBase)observable).set(0); //消除抖动
			}
		};

		ChangeListener<? super Number> leftXListener = topYListener;
		
		double width = contralBounds.getWidth();
		double height = contralBounds.getHeight();
		
		//TODO
		System.out.println("width:" + width);
		System.out.println("height:" + height);
		
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
		//关于旋转点的设置//
		////////////////////
		
		//旋转点
		rotateAnchor = new Anchor(AnchorDirection.ROTATE);
		rotateAnchor.sizeProperty().bind(anchorSizeProperty);
		rotateAnchor.layoutXProperty().bind(this.widthProperty().divide(2));
		this.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				double height = (double) newValue;
				double distence = Math.abs(rotateAnchor.layoutYProperty().get());
				if(height < 0) {
					rotateAnchor.layoutYProperty().set(distence);
				} else {
					rotateAnchor.layoutYProperty().set(-distence);
				}
			}
		});
		rotateAnchor.layoutYProperty().set(-ROTATE_POINT_DISTENCE);
		rotateAnchor.getShape().setFill(Color.YELLOW);
		rotateAnchor.getShape().strokeWidthProperty().bind(anchorStrokeWidthProperty);
		
		//初始化监听器
		//TODO
		initRotateListener();
		rotateAnchor.getShape().addEventFilter(MouseEvent.ANY,rotateListener);
		
		Line rotateLine = new Line();
		rotateLine.strokeProperty().bind(borderStrokeProperty);
		rotateLine.strokeWidthProperty().bind(borderStrokeWidthProperty);
		
		rotateLine.startXProperty().bind(top.layoutXProperty());
		rotateLine.startYProperty().bind(top.layoutYProperty());	
		rotateLine.endXProperty().bind(rotateAnchor.layoutXProperty());
		rotateLine.endYProperty().bind(rotateAnchor.layoutYProperty());
		
		//左下虚拟点
		Anchor virtualPointLB = new Anchor(AnchorDirection.LEFT_BOTTOM);
		virtualPointLB.sizeProperty().bind(anchorSizeProperty);
		virtualPointLB.layoutXProperty().bind(leftBottom.layoutXProperty());
		virtualPointLB.layoutYProperty().bind(
				leftBottom.layoutYProperty().add(rotateAnchor.layoutYProperty().multiply(-1)));
		virtualPointLB.getShape().setOpacity(0d);
		virtualPointLB.getShape().setMouseTransparent(true);
		virtualPointLB.getShape().strokeWidthProperty().bind(anchorStrokeWidthProperty);
		
		//右下虚拟点
		Anchor virtualPointRB = new Anchor(AnchorDirection.RIGHT_BOTTOM);
		virtualPointRB.sizeProperty().bind(anchorSizeProperty);
		virtualPointRB.layoutXProperty().bind(rightBottom.layoutXProperty());
		virtualPointRB.layoutYProperty().bind(
				rightBottom.layoutYProperty().add(rotateAnchor.layoutYProperty().multiply(-1)));
		virtualPointRB.getShape().setOpacity(0d);
		virtualPointRB.getShape().setMouseTransparent(true);
		virtualPointRB.getShape().strokeWidthProperty().bind(anchorStrokeWidthProperty);
		
		//旋转点面板
		rotateAnchorPane = new PenetrablePane();
		
		//位置绑定
		rotateAnchorPane.layoutXProperty().bind(this.layoutXProperty());
		rotateAnchorPane.layoutYProperty().bind(this.layoutYProperty());
		
		//位移绑定
		rotateAnchorPane.translateXProperty().bindBidirectional(this.translateXProperty());
		rotateAnchorPane.translateYProperty().bindBidirectional(this.translateYProperty());
		
		//缩放绑定
		rotateAnchorPane.scaleXProperty().bindBidirectional(this.scaleXProperty());
		rotateAnchorPane.scaleYProperty().bindBidirectional(this.scaleYProperty());
		
		//旋转绑定
		rotateAnchorPane.rotateProperty().bind(this.rotateProperty());
		
		rotateAnchorPane.getChildren().addAll(
				rotateLine,
				virtualPointLB.getShape(),
				virtualPointRB.getShape(),
				rotateAnchor.getShape());
		
		////////////////////
		//对图形添加监听器//
		////////////////////
		//addShapeAwakenListener();
		//addShapeMoveListener();
		
		//默认为不可视
		//setVisible(false);
		//rotateAnchorPane.setVisible(false);

		//this.getChildren().add(0, rotateAnchorPane);
	}
	
	/********************************************************
	 *                                                      *
	 *                  旋转监听器                *
	 *                                                      *
	 ********************************************************/
	private void initRotateListener() {
		rotateListener = new EventHandler<MouseEvent>() {
			
			private Point2D dPonit;
			private double dAngle;
			Point2D centerPosition;
			
			@Override
			public void handle(MouseEvent event) {
				
				if(event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
					centerPosition = computeCenterPosition();
					updateDate(centerPosition, event);
				} else if(event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
					
					Point2D realPosition = 
							computeRealPosition(centerPosition,event.getX(), event.getY());
					Point2D innerPosition = realPosition.subtract(centerPosition);

					double angle = innerPosition.angle(dPonit);
					
					//用于判断方向
					if(angle != 0) {
						
						double x1 = innerPosition.getX();
						double x2 = dPonit.getX();
						double y1 = innerPosition.getY();
						double y2 = dPonit.getY();
						
						double n = x1*y2 - x2*y1;
						if(n > 0) {
							angle *= -1;
						}
					}

					RectangularDrawableControlPane.this.rotateProperty().set(angle + dAngle);
				} else if(event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
					
				}
			}
			
			private void updateDate(Point2D centerPosition,MouseEvent event) {
 				Point2D realPosition = 
 						computeRealPosition(centerPosition,event.getX(), event.getY());
				dPonit = realPosition.subtract(centerPosition);
				dAngle = RectangularDrawableControlPane.this.rotateProperty().doubleValue();
			}
			
			//计算node中心位置信息
			private Point2D computeCenterPosition() {
				return CoordinateHelper.computeCenterPosition(contralBounds);
			}
			
			//计算实时位置
			private Point2D computeRealPosition(Point2D center,double nowX,double nowY) {
				return CoordinateHelper.computeRealPosition(center,Math.toRadians(getRotate()),nowX, nowY);
			}
		};
		
	}
	
	public PenetrablePane getRotateAnchorPane() {
		return rotateAnchorPane;
	}
}
