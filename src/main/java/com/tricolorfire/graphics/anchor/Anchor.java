package com.tricolorfire.graphics.anchor;

import com.tricolorfire.graphics.coordinate.CoordinateHelper;
import com.tricolorfire.graphics.coordinate.CoordinateHelper.AxisType;
import com.tricolorfire.graphics.drawable.interfaces.IBounds;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

//TODO 增加一套绑定器
public class Anchor {
	
	public static final double DEFAULT_SIZE = 10;
	public static final double DEFAULT_MIN_WIDTH = 1;
	public static final double DEFAULT_MIN_HEIGHT = 1;
	
	public static final ImageCursor ROTATECUR_CURSOR = 
			new ImageCursor(
					new Image(Anchor.class.getResource("rotate_cursor.png").toExternalForm(),256,256,true,true),
					128,128);
	public static final ImageCursor ON_ROTATECUR_CURSOR = 
			new ImageCursor(
					new Image(Anchor.class.getResource("on_rotate_cursor.png").toExternalForm(),256,256,true,true),
					128,128);
	
	//控制区域
	private IBounds contralBounds;
	
	//限制大小
	private BooleanProperty limitLengthProperty;
	private DoubleProperty minWidthProperty;
	private DoubleProperty minHeightProperty;
	
	//点坐标
	private DoubleProperty xProperty,yProperty;
	private AnchorDirection direction;
	
	//点大小
	private DoubleProperty sizeProperty;
	
	//点形状
	private Shape shape;
	
	//点监听器
	private EventHandler<MouseEvent> listener;
	
	public Anchor(AnchorDirection direction) {
		this.direction = direction;
		init();
	}
	
	public Anchor(AnchorDirection direction,IBounds contralBounds) {
		this.direction = direction;
		this.contralBounds = contralBounds;
		init();
	}
	
	/********************************************************
	 *                                                      *
	 *                  初始化                *
	 *                                                      *
	 ********************************************************/
	private void init() {
		sizeProperty = new DoublePropertyBase() {
			
			@Override
			protected void invalidated() {
				double size = get();
				
				double x = xProperty.doubleValue();
				double y = yProperty.doubleValue();
				
				if(shape != null) {
					if(shape instanceof Circle) {
						Circle circle = ((Circle)shape);
						circle.setRadius(size/2);
						circle.setCenterX(x);
						circle.setCenterY(y);
					} else if(shape instanceof Rectangle) {
						Rectangle rect = ((Rectangle)shape);
						rect.setWidth(size);
						rect.setHeight(size);
						
						double halfWidth = rect.getWidth()/2;
						rect.setX(x - halfWidth);
						double halfHeight = rect.getHeight()/2;
						rect.setY(y - halfHeight);
					}
				}
			}
			
			@Override
			public String getName() {
				return "size";
			}
			
			@Override
			public Object getBean() {
				return Anchor.this;
			}
		};
		
		limitLengthProperty = new BooleanPropertyBase() {
			
			@Override
			public String getName() {
				return "limitLenght";
			}
			
			@Override
			public Object getBean() {
				return Anchor.this;
			}
		};
		
		minWidthProperty = new DoublePropertyBase() {
			
			@Override
			public String getName() {
				return "minWidth";
			}
			
			@Override
			public Object getBean() {
				return Anchor.this;
			}
		};
		
		minHeightProperty = new DoublePropertyBase() {
			
			@Override
			public String getName() {
				return "minHeight";
			}
			
			@Override
			public Object getBean() {
				return Anchor.this;
			}
		};
		
		xProperty = new DoublePropertyBase() {
			
			@Override
			public String getName() {
				return "positionX";
			}
			
			@Override
			public Object getBean() {
				return Anchor.this;
			}
		};

		yProperty = new DoublePropertyBase() {
			
			@Override
			public String getName() {
				return "positionY";
			}
			
			@Override
			public Object getBean() {
				return Anchor.this;
			}
		};
		
		xProperty.set(0.00001);
		yProperty.set(0.00001);
		
		limitLengthProperty.set(true);
		minWidthProperty.set(DEFAULT_MIN_WIDTH);
		minHeightProperty.set(DEFAULT_MIN_HEIGHT);
		
		updateShape();
		
		shape.setFill(Color.WHITE);
		shape.setStrokeType(StrokeType.INSIDE);
		shape.setStrokeWidth(1.0);
		shape.setStroke(Color.BLACK);
		setSize(DEFAULT_SIZE);
	}
	
	/********************************************************
	 *                                                      *
	 *                  根据方向初始化锚点形状及监听器                *
	 *                                                      *
	 ********************************************************/
	private void updateShape() {
		switch(direction) {
		case TOP:
		case BOTTOM:
			tb();
			break;
			
		case LEFT:
		case RIGHT:
			lr();
			break;
			
		case LEFT_TOP:
		case RIGHT_TOP:
		case LEFT_BOTTOM:
		case RIGHT_BOTTOM:
		case OMNIBEARING:
		case NONE:
			angular();
			break;
			
		case ROTATE:
			rotate();
			break;
			
		default:
			break;
		}
		
		if(listener != null) {
			shape.addEventHandler(MouseEvent.ANY,listener);
		}
		
		if(contralBounds != null) {
			contralBounds.rotateProperty().addListener(new InvalidationListener() {
				@Override
				public void invalidated(Observable observable) {
					//根据角度自动更新鼠标样式
					Cursor cursor = changeCursor(direction, contralBounds.getRotate());
					if(!cursor.equals(shape.getCursor())) {
						shape.setCursor(cursor);
					}
				}
			});
		}
		
	}

	/********************************************************
	 *                                                      *
	 *                  边角锚点设计                *
	 *                                                      *
	 ********************************************************/
	
	private void angular() {
		shape = new Rectangle();
		Rectangle rect = ((Rectangle)shape);
		
		//使之呈圆形形状
		rect.arcWidthProperty().bind(rect.widthProperty());
		rect.arcHeightProperty().bind(rect.heightProperty());
		
		//初始化监听器
		listener = new EventHandler<MouseEvent>() {
			private double layoutX;
			private double layoutY;
			
			private double width;
			private double height;
			
			private Point2D start;
			private double dx,dy;
			
			@Override
			public void handle(MouseEvent event) {
				if(event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
					layoutX = contralBounds.getLayoutX();
					layoutY = contralBounds.getLayoutY();
					
					width = contralBounds.getWidth();
					height = contralBounds.getHeight();
					
					start = new Point2D(event.getX(), event.getY());
					
					dx = event.getX();
					dy = event.getY();
					
				} else if(event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
					
					if(contralBounds == null) {
						xProperty.set(event.getX());
						yProperty.set(event.getY());
						return;
					}
					
					double radians = Math.toRadians(contralBounds.getRotate());
					
					double x = event.getX();
					double y = event.getY();
					
					double minWidth = minWidthProperty.doubleValue();
					double minHeight = minHeightProperty.doubleValue();
					
					if(direction.equals(AnchorDirection.RIGHT_BOTTOM)) {
						
						////////
						//右下//
						////////
						
						//判断长度是否受限制
						if(limitLengthProperty.getValue()) {
							if(x < minWidth) {
								x = minWidth;
							}
							if(y < minHeight) {
								y = minHeight;
							}
						} 
						
						Point2D now = new Point2D(x,y);
						
						//x方向的差值
						Point2D xRes =
							CoordinateHelper.computeDeltaLeftTopPosition(
									start,now,width,height,radians,AxisType.X);
						//y方向的差值
						Point2D yRes =
							CoordinateHelper.computeDeltaLeftTopPosition(
									start,now,width,height,radians,AxisType.Y);
						
						//综合差值
						Point2D res = xRes.add(yRes);
						
						//重置位置信息
						contralBounds.setLayoutX(layoutX - res.getX());
						contralBounds.setLayoutY(layoutY - res.getY());
						
						//更新数据
						xProperty.set(x);
						yProperty.set(y);
						
					} else if(direction.equals(AnchorDirection.RIGHT_TOP)){
						
						////////
						//右上//
						////////
						
						dy += event.getY();
						
						//判断长度是否受限制
						if(limitLengthProperty.getValue()) {
							if(x < minWidth) {
								x = minWidth;
							}
							if(height - dy < minHeight) {
								dy = height - minHeight;
							}
						} 
						
						Point2D now = new Point2D(x, dy);
						//x方向的差值
						Point2D xRes =
							CoordinateHelper.computeDeltaLeftTopPosition(
									start, now, width, height, radians, AxisType.X);
						//y方向的差值
						Point2D yRes = CoordinateHelper.computeDeltaLeftTopPosition(
								Point2D.ZERO, now, width , height, radians, AxisType.Y);
						
						//综合差值
						Point2D res = xRes.add(yRes);
						
						//中心移位
						contralBounds.setLayoutX(layoutX - res.getX()); 
						contralBounds.setLayoutY(layoutY - res.getY() + dy);
						
						//高度控制
						contralBounds.setHeight(height - dy); 
						
						//更新数据
						xProperty.set(x);
						yProperty.set(0);
					} else if(direction.equals(AnchorDirection.LEFT_TOP)){
						
						////////
						//左上//
						////////
						
						dx += event.getX();
						dy += event.getY();
						
						//判断长度是否受限制
						if(limitLengthProperty.getValue()) {
							if(width - dx  < minWidth) {
								dx = width - minWidth;
							}
							if(height - dy < minHeight) {
								dy = height - minHeight;
							}
						} 
						
						Point2D now = new Point2D(dx,dy);
						
						//x方向的差值
						Point2D xRes = CoordinateHelper.computeDeltaLeftTopPosition(
								Point2D.ZERO, now, width, height, radians, AxisType.X);
						
						//y方向的差值
						Point2D yRes = CoordinateHelper.computeDeltaLeftTopPosition(
								Point2D.ZERO, now, width, height, radians, AxisType.Y);
						
						//综合差值
						Point2D res = xRes.add(yRes);
						
						//中心移位
						contralBounds.setLayoutX(layoutX - res.getX() + dx); 
						contralBounds.setLayoutY(layoutY - res.getY() + dy);
						
						//宽度控制 和 高度控制
						contralBounds.setHeight(height - dy); 
						contralBounds.setWidth(width - dx);
						
						//更新数据
						xProperty.set(0);
						yProperty.set(0);
						
					} else if(direction.equals(AnchorDirection.LEFT_BOTTOM)){

						////////
						//左下//
						////////
						
						dx += event.getX();
						
						//判断长度是否受限制
						if(limitLengthProperty.getValue()) {
							if(width - dx  < minWidth) {
								dx = width - minWidth;
							}
							if(y < minHeight) {
								y = minHeight;
							}
						} 
						
						Point2D now = new Point2D(dx,y);
						//x方向的差值
						Point2D xRes = CoordinateHelper.computeDeltaLeftTopPosition(
								Point2D.ZERO, now, width, height, radians, AxisType.X);
						
						//y方向的差值
						Point2D yRes =
						CoordinateHelper.computeDeltaLeftTopPosition(
								start, now, width , height, radians, AxisType.Y);
						
						//综合差值
						Point2D res = xRes.add(yRes);
						
						//中心移位
						contralBounds.setLayoutX(layoutX - res.getX() + dx); 
						contralBounds.setLayoutY(layoutY - res.getY());
						
						//宽度控制
						contralBounds.setWidth(width - dx); 
						
						//更新数据
						xProperty.set(0);
						yProperty.set(y);
					} 
					
				} else if(event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
					
				}
			}
		};
		
		switch(direction) {
		case LEFT_TOP:
			shape.setCursor(Cursor.NW_RESIZE);
			break;
		case RIGHT_TOP:
			shape.setCursor(Cursor.NE_RESIZE);
			break;
		case LEFT_BOTTOM:
			shape.setCursor(Cursor.SW_RESIZE);
			break;
		case RIGHT_BOTTOM:
			shape.setCursor(Cursor.SE_RESIZE);
			break;
		default:
			break;
		}
		
		if(contralBounds != null) {
			initWidthandHeightChangeListener();
		}
		
		initChangeListener();
	}

	private void initWidthandHeightChangeListener() {
		
		ChangeListener<Number> lenChangeListener = new ChangeListener<Number>() {

			int len;
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				int newLen = Double.valueOf((double) newValue).intValue();
				int oldLen = Double.valueOf((double) oldValue).intValue();
				int n = newLen*oldLen;
				
				if(n == 0) {
					if(oldLen != 0) {
						len = oldLen;
						return;
					}
					n = len*newLen;
				}
				
				if(n < 0) {
					if(contralBounds.getRotate() == 0) {
						Cursor rcursor = reverseDirectionCursor(shape.getCursor());
						shape.setCursor(rcursor);
					}
				}
			}
		};
		contralBounds.widthProperty().addListener(lenChangeListener);
		contralBounds.heightProperty().addListener(lenChangeListener);
	}

	private Cursor reverseDirectionCursor(Cursor cursor) {
		Cursor ret;
		if(cursor.equals(Cursor.N_RESIZE)) {
			ret = Cursor.S_RESIZE;
		} else if(cursor.equals(Cursor.NE_RESIZE)) {
			ret = Cursor.NW_RESIZE;
		} else if(cursor.equals(Cursor.E_RESIZE)) {
			ret = Cursor.W_RESIZE;
		} else if(cursor.equals(Cursor.SE_RESIZE)) {
			ret = Cursor.SW_RESIZE;
		} else if(cursor.equals(Cursor.S_RESIZE)) {
			ret = Cursor.N_RESIZE;
		} else if(cursor.equals(Cursor.SW_RESIZE)) {
			ret = Cursor.SE_RESIZE;
		} else if(cursor.equals(Cursor.W_RESIZE)) {
			ret = Cursor.E_RESIZE;
		} else if(cursor.equals(Cursor.NW_RESIZE)) {
			ret = Cursor.NE_RESIZE;
		} else {
			ret = cursor;
		}
		return ret;
	}
	
	/********************************************************
	 *                                                      *
	 *                  上下锚点设计                *
	 *                                                      *
	 ********************************************************/
	private void tb() {
		shape = new Rectangle();
		listener = new EventHandler<MouseEvent>() {
			private double layoutX;
			private double layoutY;
			
			private double width;
			private double height;
			
			private Point2D start;
			
			private double dy;
			@Override
			public void handle(MouseEvent event) {
				
				if(event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
					
					layoutX = contralBounds.getLayoutX();
					layoutY = contralBounds.getLayoutY();
					
					width = contralBounds.getWidth();
					height = contralBounds.getHeight();
					
					start = new Point2D(event.getX(), event.getY());
					dy = event.getY();
				} else if(event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
					
					if(contralBounds == null) {
						yProperty.set(event.getY());
						return;
					}
					
					double radians = Math.toRadians(contralBounds.getRotate());
					double x = event.getX();
					double y = event.getY();

					double minHeight = minHeightProperty.doubleValue();
					
					if(direction.equals(AnchorDirection.BOTTOM)) {
						
						//判断长度是否可以受限制
						if(limitLengthProperty.getValue()) {
							if(y < minHeight) {
								y = minHeight;
							} 
						} 
						
						Point2D now = new Point2D(x,y);
						Point2D res =
							CoordinateHelper.computeDeltaLeftTopPosition(
									start, now, width, height, radians, AxisType.Y);
						
						//重置位置信息
						contralBounds.setLayoutX(layoutX - res.getX());
						contralBounds.setLayoutY(layoutY - res.getY());
						
						//更新数据
						yProperty.set(y);
						
					} else if(direction.equals(AnchorDirection.TOP)) {
						dy += event.getY();
						
						//判断长度是否可以受限制
						if(limitLengthProperty.getValue()) {
							if(height - dy < minHeight) {
								dy = height - minHeight;
							} 
						} 
						
						Point2D now = new Point2D(0, dy);
						Point2D res = CoordinateHelper.computeDeltaLeftTopPosition(
								Point2D.ZERO, now, width, height, radians, AxisType.Y);
						
						//中心移位
						contralBounds.setLayoutX(layoutX - res.getX()); 
						contralBounds.setLayoutY(layoutY - res.getY() + dy);
						
						//宽度控制
						contralBounds.setHeight(height - dy); 
						
						//更新数据
						yProperty.set(0);
					}
					
				} else if(event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
					
				}
			}
		};
		
		switch(direction) {
		case TOP:
			shape.setCursor(Cursor.N_RESIZE);
			break;
		case BOTTOM:
			shape.setCursor(Cursor.S_RESIZE);
			break;
		default:
			break;
		}
		
		initChangeListener();
	}

	/********************************************************
	 *                                                      *
	 *                  左右锚点设计                *
	 *                                                      *
	 ********************************************************/
	private void lr() {
		shape = new Rectangle();
		
		listener = new EventHandler<MouseEvent>() {
			
			private double layoutX;
			private double layoutY;
			
			private double width;
			private double height;
			
			private Point2D start;
			
			private double dx;
			@Override
			public void handle(MouseEvent event) {
				
				if(event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
					
					layoutX = contralBounds.getLayoutX();
					layoutY = contralBounds.getLayoutY();
					
					width = contralBounds.getWidth();
					height = contralBounds.getHeight();
					
					start = new Point2D(event.getX(), event.getY());
					
					dx = event.getX();
				} else if(event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
					
					if(contralBounds == null) {
						xProperty.set(event.getX());
						return;
					}
					
					double radians = Math.toRadians(contralBounds.getRotate());
					double x = event.getX();
					double y = event.getY();
					
					double minWidth = minWidthProperty.doubleValue();
					
					if(direction.equals(AnchorDirection.RIGHT)) {
						
						//判断长度是否受限制
						if(limitLengthProperty.getValue()) {
							if(x < minWidth) {
								x = minWidth;
							} 
						} 
						
						Point2D now = new Point2D(x,y);
						Point2D res = 
							CoordinateHelper.computeDeltaLeftTopPosition(
									start, now, width, height, radians, AxisType.X);
						
						//重置位置信息
						contralBounds.setLayoutX(layoutX - res.getX());
						contralBounds.setLayoutY(layoutY - res.getY());
						
						//更新数据
						xProperty.set(x);
					} else if(direction.equals(AnchorDirection.LEFT)) {
						dx += event.getX();
						
						//判断长度是否受限制
						if(limitLengthProperty.getValue()) {
							if(width - dx < minWidth) {
								dx = width - minWidth;
							} 
						} 
						
						Point2D now = new Point2D(dx,0);
						Point2D res = CoordinateHelper.computeDeltaLeftTopPosition(
								Point2D.ZERO, now, width, height, radians, AxisType.X);
						
						//中心移位
						contralBounds.setLayoutX(layoutX - res.getX() + dx); 
						contralBounds.setLayoutY(layoutY - res.getY());
						
						//宽度控制
						contralBounds.setWidth(width - dx); 
						
						//更新数据
						xProperty.set(0);
					}
					
				} else if(event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
					
				}
			}
		};
		
		switch(direction) {
		case LEFT:
			shape.setCursor(Cursor.W_RESIZE);
			break;
		case RIGHT:
			shape.setCursor(Cursor.E_RESIZE);
			break;
		default:
			break;
		}
		
		initChangeListener();
	}
	
	/********************************************************
	 *                                                      *
	 *                 角度锚点设计                *
	 *                                                      *
	 ********************************************************/
	private void rotate() {
		shape = new Rectangle();
		Rectangle rect = ((Rectangle)shape);
		//使之呈圆形形状
		rect.arcWidthProperty().bind(rect.widthProperty());
		rect.arcHeightProperty().bind(rect.heightProperty());
		
		rect.setCursor(ROTATECUR_CURSOR);
		
		listener = new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				if(event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
					rect.setCursor(ON_ROTATECUR_CURSOR);
				} else if(event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
					rect.setCursor(ROTATECUR_CURSOR);
				}
			}
		};
		
		initChangeListener();
	}
	
	/********************************************************
	 *                                                      *
	 *                  锚点鼠标样式随角度的改变               *
	 *                                                      *
	 ********************************************************/
	private Cursor changeCursor(AnchorDirection direction,double tRotate) {
		Cursor cursor;
		
		double rotate = tRotate%360;
		if(rotate < 0) {
			rotate += 360;
		}
		
		//角度不变化的范围为45
		//这里取45/2
		int startAngle = 27; 
		
		//检查开始角度
		switch(direction) {
		//如果不是方向指针
		default:
			return shape.getCursor();
		//逆时针方向
		case LEFT_TOP:
			startAngle += 45;
		case LEFT:
			startAngle += 45;
		case LEFT_BOTTOM:
			startAngle += 45;
		case BOTTOM:
			startAngle += 45;
		case RIGHT_BOTTOM:
			startAngle += 45;
		case RIGHT:
			startAngle += 45;
		case RIGHT_TOP:
			startAngle += 45;
		case TOP:
			startAngle += 0;
		}
		
		int n = (((int)rotate + startAngle)/45)%8;
		switch(n) {
		case 0:
			cursor = Cursor.N_RESIZE;
			break;
		case 1:
			cursor = Cursor.NE_RESIZE;
			break;
		case 2:
			cursor = Cursor.E_RESIZE;
			break;
		case 3:
			cursor = Cursor.SE_RESIZE;
			break;
		case 4:
			cursor = Cursor.S_RESIZE;
			break;
		case 5:
			cursor = Cursor.SW_RESIZE;
			break;
		case 6:
			cursor = Cursor.W_RESIZE;
			break;
		case 7:
			cursor = Cursor.NW_RESIZE;
			break;
		default:
			cursor = shape.getCursor();
			break;
		}
		
		return cursor;
	}
	
	/********************************************************
	 *                                                      *
	 *                  位置监听器                *
	 *                                                      *
	 ********************************************************/
	private void initChangeListener() {
		Rectangle rect = ((Rectangle)shape);
		
		ChangeListener<? super Number> xChangelistener = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				double x = (double)newValue;
				double halfWidth = rect.getWidth()/2;
				rect.setX(x - halfWidth);
			}
		};
		xProperty.addListener(xChangelistener);
		
		ChangeListener<? super Number> yChangelistener = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				double y = (double)newValue;
				double halfHeight = rect.getHeight()/2;
				rect.setY(y - halfHeight);
			}
		};
		yProperty.addListener(yChangelistener);
	}

	/********************************************************
	 *                                                      *
	 *                   Setter 和 Getter                    *
	 *                                                      *
	 ********************************************************/
	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}
	
	public AnchorDirection getDirection() {
		return direction;
	}

	public void setDirection(AnchorDirection direction) {
		this.direction = direction;
		updateShape();
	}

	public double getSize() {
		return sizeProperty.get();
	}

	public void setSize(double size) {
		sizeProperty.set(size);
	}
	
	public DoubleProperty sizeProperty() {
		return sizeProperty;
	}

	public DoubleProperty minWidthProperty() {
		return minWidthProperty;
	}

	public DoubleProperty xProperty() {
		return xProperty;
	}
	
	public DoubleProperty yProperty() {
		return yProperty;
	}
	
	public void setX(double x) {
		xProperty.set(x);
	}
	
	public double getX() {
		return xProperty.get();
	}
	
	public void setY(double y) {
		yProperty.set(y);
	}
	
	public double getY() {
		return yProperty.get();
	}
	
	public void setMinWidthProperty(double minWidth) {
		this.minWidthProperty.set(minWidth);
	}

	public DoubleProperty minHeightProperty() {
		return minHeightProperty;
	}

	public void setMinHeightProperty(double minHeight) {
		this.minHeightProperty.set(minHeight);
	}

	public BooleanProperty limitLengthProperty() {
		return limitLengthProperty;
	}

	public void setLimitLengthProperty(boolean limitLength) {
		this.limitLengthProperty.set(limitLength);
	}
	
}
