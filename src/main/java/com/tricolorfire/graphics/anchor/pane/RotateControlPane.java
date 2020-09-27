package com.tricolorfire.graphics.anchor.pane;

import com.tricolorfire.graphics.anchor.Anchor;
import com.tricolorfire.graphics.anchor.AnchorDirection;
import com.tricolorfire.graphics.anchor.IScaleAdapter;
import com.tricolorfire.graphics.coordinate.CoordinateHelper;
import com.tricolorfire.graphics.drawable.interfaces.IBounds;
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

public class RotateControlPane extends PenetrablePane implements IScaleAdapter{
	
	public static final double ROTATE_POINT_DISTENCE = 30;
	
	public static final double DEFAULT_BORDER_STROKE_WIDTH = 1.0;
	public static final Paint DEFAULT_BORDER_STROKE = Color.BLUE;
	public static final Paint DEFAULT_FILL = Color.YELLOW;
	
	public static final double DEFAULT_ANCHOR_SIZE = 8.0; 
	public static final double DEFAULT_ANCHOR_STRPKE_WIDTH = 1.0;
	
	//旋转节点配置
	private Anchor rotateAnchor;
	private EventHandler<MouseEvent> rotateListener;
	
	//主要控制区域
	private IBounds controlBounds;
	
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
	
	public RotateControlPane(IBounds contralBounds) {
		this.controlBounds = contralBounds;
		init();
	}
	
	private void init() {
		
		double width = controlBounds.getWidth();
		//double height = controlBounds.getHeight();
		
		ChangeListener<? super Number> topYListener = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				//删掉似乎也没影响
				((DoublePropertyBase)observable).set(0); //消除抖动
			}
		};
		ChangeListener<? super Number> leftXListener = topYListener;
		//上
		Anchor top = new Anchor(AnchorDirection.TOP,controlBounds);
		top.setLocation(width/2 , 0);
		top.sizeProperty().bind(anchorSizeProperty);
		top.layoutXProperty().bind(
				controlBounds.widthProperty().divide(2));
		top.layoutYProperty().addListener(topYListener);
		top.getShape().strokeWidthProperty().bind(anchorStrokeWidthProperty);
		
		////////////////////
		//关于旋转点的设置//
		////////////////////
		
		//旋转点
		rotateAnchor = new Anchor(AnchorDirection.ROTATE);
		rotateAnchor.sizeProperty().bind(anchorSizeProperty);
		rotateAnchor.layoutXProperty().bind(controlBounds.widthProperty().divide(2));
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
		rotateAnchor.getShape().setFill(DEFAULT_FILL);
		rotateAnchor.getShape().strokeWidthProperty().bind(anchorStrokeWidthProperty);
		
		//初始化监听器
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
		virtualPointLB.layoutXProperty().addListener(leftXListener);
		virtualPointLB.layoutYProperty().bind(
				controlBounds.heightProperty().add(rotateAnchor.layoutYProperty().multiply(-1)));
		virtualPointLB.getShape().setOpacity(0d);
		virtualPointLB.getShape().setMouseTransparent(true);
		virtualPointLB.getShape().strokeWidthProperty().bind(anchorStrokeWidthProperty);
		
		//右下虚拟点
		Anchor virtualPointRB = new Anchor(AnchorDirection.RIGHT_BOTTOM);
		virtualPointRB.sizeProperty().bind(anchorSizeProperty);
		virtualPointRB.layoutXProperty().bind(controlBounds.widthProperty());
		virtualPointRB.layoutYProperty().bind(
				controlBounds.heightProperty().add(rotateAnchor.layoutYProperty().multiply(-1)));
		virtualPointRB.getShape().setOpacity(0d);
		virtualPointRB.getShape().setMouseTransparent(true);
		virtualPointRB.getShape().strokeWidthProperty().bind(anchorStrokeWidthProperty);
		
		//位置绑定
		this.layoutXProperty().bind(controlBounds.layoutXProperty());
		this.layoutYProperty().bind(controlBounds.layoutYProperty());
		
		//位移绑定
		this.translateXProperty().bindBidirectional(controlBounds.translateXProperty());
		this.translateYProperty().bindBidirectional(controlBounds.translateYProperty());
		
		//缩放绑定
		this.scaleXProperty().bindBidirectional(controlBounds.scaleXProperty());
		this.scaleYProperty().bindBidirectional(controlBounds.scaleYProperty());
		
		//旋转绑定
		this.rotateProperty().bind(controlBounds.rotateProperty());
		
		this.getChildren().addAll(
				rotateLine,
				virtualPointLB.getShape(),
				virtualPointRB.getShape(),
				rotateAnchor.getShape());
		
		//默认为不可视
		//setVisible(false);
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

					controlBounds.rotateProperty().set(angle + dAngle);
				} else if(event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
					
				}
			}
			
			private void updateDate(Point2D centerPosition,MouseEvent event) {
 				Point2D realPosition = 
 						computeRealPosition(centerPosition,event.getX(), event.getY());
				dPonit = realPosition.subtract(centerPosition);
				dAngle = controlBounds.rotateProperty().doubleValue();
			}
			
			//计算node中心位置信息
			private Point2D computeCenterPosition() {
				return CoordinateHelper.computeCenterPosition(controlBounds);
			}
			
			//计算实时位置
			private Point2D computeRealPosition(Point2D center,double nowX,double nowY) {
				return CoordinateHelper.computeRealPosition(center,Math.toRadians(getRotate()),nowX, nowY);
			}
		};
		
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
		
		//长度自适应
		double d = rotateAnchor.layoutYProperty().get();
		double realD = ROTATE_POINT_DISTENCE/scale;
		if(d < 0) {
			realD *= -1;
		}
		rotateAnchor.layoutYProperty().set(realD);
	}
	
}
