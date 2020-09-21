package com.tricolorfire.graphics.ui;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * TODO 
 *  设置初始显示数值
 *        设置两个Property分别对应V,H标尺，用于互相绑定
 *  
 *  //鼠标监听器设计 
 *  //鼠标指示图层设计
 *  //自动变换设计
 *  //经纬度截断定位设计(包含允许截断等)
 *  //尺子截断设计（居中截断），对应经纬度截断设计
 *  
 *  //获取鼠标指示的刻度
 *  //鼠标刻度改变监听器
 *
 */
public class StaffGauge extends Pane{
	
	public static final double DEFAULT_SCALE_UNIT_FRIST  = 1.0;
	public static final double DEFAULT_SCALE_UNIT_SECOND = 10.0;
	public static final double DEFAULT_UNIT_LENGHT_POWER  = 1.0;
	public static final double DEFAULT_SCALE_NUMBER_POWER = 5.0;
	public static final double DEFAULT_UNIT_LENGHT  = 5.0;
	public static final double DEFAULT_MAX_UNIT_LENGHT = 16.0;
	public static final double DEFAULT_MIN_UNIT_LENGHT = 4.0;
	
	public static final Color DEFAULT_TICK_MARK_COLOR = Color.BLACK;
	public static final Color DEFAULT_RULE_BACKGROUND_COLOR = Color.web("#E0E0E0");
	public static final Color DEFAULT_MARK_LINE_COLOR = Color.BLUE;
	
	public static final double DEFAULT_SCALE_TEXT_FONT_SIZE = 12.0;
	
	private Canvas ruleCanvas;
	private Canvas markLayer;
	
	private Scene currentScene;              //当前窗口
	private boolean adaptiveLength;          //自适应长度
	
	private ChangeListener<Number> SceneSizeListener;  //窗口监听器
	private EventHandler<MouseEvent> mouseMoveListener;//鼠标监听器
	private EventHandler<MouseEvent> mouseExitListener;//鼠标退出监听器
	
	private Set<PositionChangeListener> posListeners;  //自身的监听器
	
	private Color tickMarkColor;        //刻度线颜色
	private Color ruleBackgroundColor;  //标尺背景颜色
	private Color markLineColor;        //标记线颜色
	
	public enum Direction {
		VERTICAL,	//垂直方向
		HORIZONTAL; //水平方向
	}	
	
	private Direction direction;		//标尺方向
	
	private double unitLenghtPower;     //单位长度放大倍数
	private double scaleNumberPower;    //刻度数值放大倍数
	private double scaleUnit[];         //刻度单位,按从小到大排列
	                                    //计量方案如下: 1.0 - 10.0
	                                    //意为，每10个单位记录一个大格，每1个单位记录一个小格
										//主要用于标识数字
	
	private double origin;              //原点位置
	private double absLTX,absLTY;       //左上角相对于应用的绝对坐标
	private double absRBX,absRBY;       //右下角相对于应用的绝对坐标
	
	private double unitLenght;          //单位长度
	//private double ruleLenght;        //标尺长度(单位为像素)
	//private double ruleStart,ruleEnd; //标尺的刻度开始显示的位置，结束显示的位置
	
	private boolean limitUnitLenght;    //是否限制单位长度[true限制,false不限制]
	private double maxUnitLenght;       //最大单位长度
	private double minUnitLenght;       //最小单位长度
	
	private boolean absoluteScale;      //对刻度取绝对值[true:取绝对值,false:不取绝对值]
	private boolean showScaleText;      //刻度数值显示
	private boolean showMarkLine;       //标记线
	
	//循环模式
	public enum CycleMode{
		NONE,
		GLOBAL,
		ADAPTION
	}
	private CycleMode cycleScale;       //循环刻度
	private double cycleSize;           //周期大小
	
	public StaffGauge() {
		super();
		init(new double[] {DEFAULT_SCALE_UNIT_FRIST,DEFAULT_SCALE_UNIT_SECOND},
			0.0,
			Direction.HORIZONTAL,
			true,
			true);
	}
	
	public StaffGauge(double[] scaleUnit) {
		super();
		init(scaleUnit,0.0,Direction.HORIZONTAL,true,true);
	}
	
	public StaffGauge(double[] scaleUnit,double origin,Direction dir) {
		super();
		init(scaleUnit,origin,dir,true,true);
	}
	
	public StaffGauge(
			double[] scaleUnit,
			double origin,
			Direction dir,
			boolean absoluteScale) {
		super();
		init(scaleUnit,origin,dir,absoluteScale,true);
	}
	
	public StaffGauge(
			double[] scaleUnit,
			double origin,
			Direction dir,
			boolean absoluteScale,
			boolean adaptiveLength) {
		super();
		init(scaleUnit,origin,dir,absoluteScale,adaptiveLength);
	}
	
	//TODO
    /***************************************************************************
     *                                                                         *
     *                         初始化设置                            							*
     *                                                                         *
     **************************************************************************/
	//初始化设置
	private void init(
			double scaleUnit[],
			Double origin,
			Direction direction,
			boolean absoluteScale,
			boolean adaptiveLength) {
		
		//初始化数据
		this.scaleUnit = scaleUnit;
		this.origin = origin;
		this.direction = direction;
		this.absoluteScale = absoluteScale;
		this.adaptiveLength = adaptiveLength;
		
		//TODO 默认初始化设置
		showScaleText = true;
		showMarkLine = true;
		limitUnitLenght = true;
		cycleScale = CycleMode.NONE;
		unitLenghtPower = DEFAULT_UNIT_LENGHT_POWER;
		scaleNumberPower = DEFAULT_SCALE_NUMBER_POWER;
		unitLenght = DEFAULT_UNIT_LENGHT;
		maxUnitLenght = DEFAULT_MAX_UNIT_LENGHT;
		minUnitLenght = DEFAULT_MIN_UNIT_LENGHT;
		//颜色设置
		tickMarkColor = DEFAULT_TICK_MARK_COLOR;
		ruleBackgroundColor = DEFAULT_RULE_BACKGROUND_COLOR;
		markLineColor = DEFAULT_MARK_LINE_COLOR;
		
		//初始化内部监听器
		posListeners = new HashSet<StaffGauge.PositionChangeListener>();
		
        ruleCanvas = new Canvas();
        markLayer = new Canvas();
        this.getChildren().addAll(ruleCanvas,markLayer);
        //添加监听器
        InvalidationListener listener = new InvalidationListener(){
            @Override
            public void invalidated(Observable o) {
            	
            	if(!adaptiveLength) {
            		//只运行一次
            		if(ruleCanvas.getWidth() == 0 || ruleCanvas.getHeight() == 0) {
                    	//画出图形
                    	redraw();
            		}
            		return;
            	}
            	
            	if(currentScene == null) {
            		//获取当前窗口
            		//currentScene = ApplicationUtil.getCurrentScene(StaffGauge.this);
            		currentScene = StaffGauge.this.getScene();
            		if(currentScene == null) return;
            		
            		//初始化监听器
            		initListener();
            		
            		//添加屏幕监听器
                    currentScene.widthProperty().addListener(SceneSizeListener);
                    currentScene.heightProperty().addListener(SceneSizeListener);
                    
                    //添加鼠标监听器
            		currentScene.addEventFilter(MouseEvent.MOUSE_MOVED, mouseMoveListener);
            		currentScene.addEventFilter(MouseEvent.MOUSE_DRAGGED, mouseMoveListener);
            		currentScene.addEventFilter(MouseEvent.MOUSE_EXITED, mouseExitListener);
            	}
            	
            	//画出图形
            	redraw();
            }
        };
        
        widthProperty().addListener(listener);
        heightProperty().addListener(listener);
        
        markLayer.widthProperty().bind(ruleCanvas.widthProperty());
        markLayer.heightProperty().bind(ruleCanvas.heightProperty());
        
        //setStartScaleNumber(88);
	}
	
	//TODO
    /***************************************************************************
     *                                                                         *
     *                         外部监听器初始化                            							*
     *                                                                         *
     **************************************************************************/
	private double oldValue = 0;
	//监听器初始化
	private void initListener() {
		//窗口大小监听器
		if(SceneSizeListener == null) {
    		//监听器
    		SceneSizeListener = new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue,
						Number newValue) {
    				//如果窗口缩小
    				if(adaptiveLength && ((Double)oldValue) > ((Double)newValue)) {
    					ruleCanvas.setWidth(0);
    	            	ruleCanvas.setHeight(0);
    	            	autosize();
    				}
				}
            };
		}
		
		//鼠标移动监听器
		if(mouseMoveListener == null) {
	        mouseMoveListener = new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {

					double realX = event.getX() - StaffGauge.this.localToScene(0, 0).getX(); 
					double realY = event.getY() - StaffGauge.this.localToScene(0, 0).getY();
					
					if(withinTheScope(realX,realY)) {
						//画鼠标标记线
						if(showMarkLine) {
							drawMarkLine(realX, realY);
						}
						//对内部监听器进行处理
						if(!posListeners.isEmpty()) {
							
							double newValue = 0;
							switch(direction) {
							case VERTICAL:
								newValue = computePosition(realY);
								break;
							case HORIZONTAL:
							default:
								newValue = computePosition(realX);
							}
							
							for(PositionChangeListener listener:posListeners) {
								listener.changed(direction, oldValue, newValue);
							}
							oldValue = newValue;
						}
					}
				}
			};
		}
		
		if(mouseExitListener == null) {
			mouseExitListener = new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					clearMarkLine();
				}
			};
		}
	}
	
	private double snp,ulp;
	private void updateULPandSNP() {
		//刻度数字单位大小设置
		snp = scaleNumberPower!=0?scaleNumberPower:1;
		ulp = unitLenghtPower!=0?unitLenghtPower:1;
		if(cycleScale == CycleMode.ADAPTION) {
			double ruleLenght ;
			switch(direction) {
			case VERTICAL:
				ruleLenght = getHeight()-absLTY-absRBY;
			case HORIZONTAL:
			default:
				ruleLenght = getWidth()-absLTX-absRBX;
			}
			if(ruleLenght > 0) {
				double ul = ruleLenght/cycleSize;
				snp = 1;
				ulp = ul/unitLenght;
			}
		} else if(limitUnitLenght) {//如果限制单位大小
			double now = unitLenght*ulp;
			if(now < minUnitLenght) {
				snp = scaleNumberPower*(minUnitLenght/now);
				ulp = minUnitLenght/unitLenght;
			} else if(now > maxUnitLenght) {
				snp = scaleNumberPower*(maxUnitLenght/now);
				ulp = maxUnitLenght/unitLenght;
			}
		}
	}
	
	public double getPosition() {
		return oldValue;
	}
	
	//检查坐标是否在区域内
	public boolean withinTheScope(double x,double y) {
		switch(direction) {
		case VERTICAL:
			if(absLTY <= y && y <= getHeight()-absRBY) {
				return true;
			} else return false;
		case HORIZONTAL:
		default:
			if(absLTX <= x && x <= getWidth() - absRBX) {
				return true;
			} else return false;
		}
	}

	//窗口变更(导致监听器变更)
	private void changeScene(Scene scene) {
		if(scene != null && !scene.equals(currentScene)) {
    		//初始化监听器
    		initListener();
    		if(currentScene != null) {
    			//删除屏幕监听器
    			currentScene.widthProperty().removeListener(SceneSizeListener);
    			currentScene.heightProperty().removeListener(SceneSizeListener);
                //删除鼠标监听器
        		currentScene.removeEventFilter(MouseEvent.MOUSE_MOVED, mouseMoveListener);
        		currentScene.removeEventFilter(MouseEvent.MOUSE_DRAGGED, mouseMoveListener);
        		currentScene.removeEventFilter(MouseEvent.MOUSE_EXITED, mouseExitListener);
    		}
    		//新窗口添加屏幕监听器
        	scene.widthProperty().addListener(SceneSizeListener);
        	scene.heightProperty().addListener(SceneSizeListener);
            //新窗口添加鼠标监听器
    		scene.addEventFilter(MouseEvent.MOUSE_MOVED, mouseMoveListener);
    		scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, mouseMoveListener);
    		scene.addEventFilter(MouseEvent.MOUSE_EXITED, mouseExitListener);
    		currentScene = scene;
		}
	}

	//计算位置
	private double computePosition(double p) {
		if(scaleUnit[0]==0 || unitLenght == 0) {
			System.out.println(" scaleUnit[0] 或 unitLenght 不能为0");
			return 0;
		}
		double realScaleUnit = scaleUnit[0]*snp;
		double realUnitLenght = unitLenght*ulp;
		
		//计算位置
		double num = (p - origin)/realUnitLenght;
		double res = 0d;
		if(cycleScale!=CycleMode.NONE && cycleSize > 0d) {
			res = Math.abs(cycleSize + (num*realScaleUnit)%cycleSize)%cycleSize;
		} else {
			res = (absoluteScale?Math.abs(num*realScaleUnit):num*scaleUnit[0]);
		}
		return res;
	}
	
	//TODO 设置初始显示的数值
	public void setStartScaleNumber(double s){
		//先更新ulp和snp
		if(ulp == 0d || snp == 0d) {
			updateULPandSNP();
		}
		if(scaleUnit[0]==0 || unitLenght == 0) {
			System.out.println(" scaleUnit[0] 或 unitLenght 不能为0");
			return;
		}
		double realScaleUnit = scaleUnit[0]*snp;
		double realUnitLenght = unitLenght*ulp;
		
		//获取对应s的 像素坐标
		double tmp = (s/realScaleUnit)*realUnitLenght;
		switch(direction) {
		case VERTICAL:
			origin = absLTY - tmp;
		case HORIZONTAL:
		default:
			origin = absLTX - tmp;
		}
	}
	
	//TODO
	/***************************************************************************
     *                                                                         *
     *                         图层绘画                            							*
     *                                                                         *
     **************************************************************************/
	//重新画
	private void redraw() {
    	if(!ruleCanvas.widthProperty().getValue().equals(getWidth())
    		||!ruleCanvas.heightProperty().getValue().equals(getHeight())) {
    		//自适应大小
    		ruleCanvas.setWidth(getWidth());
    		ruleCanvas.setHeight(getHeight());
    	}

    	//画刻度线
		drawTickMark(ruleCanvas.getGraphicsContext2D());
	}
	
	//清除标记线
	public void clearMarkLine() {
		markLayer.getGraphicsContext2D().clearRect(0, 0, getWidth(), getHeight());
	}
	
	//画鼠标位置线
    public void drawMarkLine(double cursorX, double cursorY) {
		
    	GraphicsContext gc = markLayer.getGraphicsContext2D();
		//清空以前的标记
    	clearMarkLine();
    	//刻度线描绘
	    gc.setFill(markLineColor);
	    
    	switch(direction) {
		case VERTICAL:
			gc.fillRect(
					absLTX,
					cursorY,
					getWidth() - absLTX - absRBX,
					1);
			break;
		case HORIZONTAL:
		default:
			gc.fillRect(
				cursorX,
				absLTY,
				1,
				getHeight() - absLTY - absRBY);
		}
	}
    
	//画刻度线
	private synchronized void drawTickMark(GraphicsContext gc) {
		//尺子背景颜色填充
		gc.setFill(ruleBackgroundColor);
		gc.fillRect(
				absLTX, 
				absLTY, 
				getWidth() - absLTX - absRBX, 
				getHeight() - absLTY - absRBY);
		
		//刻度线描绘
	    gc.setFill(tickMarkColor);
	    
		switch(direction) {
		case VERTICAL:
			drawVerticalTickMark(gc);
			break;
		case HORIZONTAL:
		default:drawHorizontalTickMark(gc);
		}
	}
	
	//刻度数字格式化
	private String scaleNumberFormat(double s) {
		return String.format("%.4f", s).replaceAll("0+?$", "").replaceAll("[.]$", "");
	}
	
	//TODO 画水平线
	private void drawHorizontalTickMark(GraphicsContext gc) {
		gc.fillRect(
				absLTX,
				absLTY,
				getWidth()  - absRBX - absLTX, 
				1);
		
		gc.fillRect(
				absLTX,
				getHeight() - absRBY - 1,
				getWidth()  - absRBX - absLTX, 
				1);
		
		gc.fillRect(
				absLTX,
				absLTY,
				1,
				getHeight() - absLTY - absRBY);
		//TODO　虚拟数值 测试使用
		////////////////////////////////
		//cycleScale = CycleMode.ADAPTION;
		//cycleScale = CycleMode.GLOBAL;
		//cycleSize = 100.0;
		//origin = 202;
		////////////////////////////////
		
		//TODO
		double n = scaleUnit[1]/scaleUnit[0];
		double halfN = n/2;
		
		//刻度高度确定
		double longLineLen = getHeight() - absLTY - absRBY;
		double middleLineLen = longLineLen/2;
		double normalLineLen = longLineLen/4;
		//字体大小确定
		gc.setFont(Font.font(Math.min(middleLineLen,DEFAULT_SCALE_TEXT_FONT_SIZE)));
		double fontSize = gc.getFont().getSize();
		//Y坐标确定
		double longLineScaleY = absLTY ;
		double normalLineScaleY = getHeight() - absRBY - normalLineLen;
		double middleLineScaleY = getHeight() - absRBY - middleLineLen;
		double scaleTextY = absLTY + (fontSize + longLineLen - normalLineLen)/2-2;
		
		//刻度数字单位大小设置
		updateULPandSNP();
		if(scaleUnit[0]==0 || unitLenght == 0) {
			System.out.println(" scaleUnit[0] 或 unitLenght 不能为0");
			return;
		}
		double realScaleUnit = scaleUnit[0]*snp;
		double realUnitLenght = unitLenght*ulp;
		
		//设置开始位置
		double start = 0;
		
		//正向画坐标 循环数值判定
		for(double i = start; origin + i*realUnitLenght < getWidth()-absRBX; i++) {
			//判断越界
			if(origin + i*realUnitLenght < absLTX) continue;
			
			double startX = origin + i*realUnitLenght;
			if(i%halfN != 0) {
				gc.fillRect(
						startX, 
						normalLineScaleY, 
						1, 
						normalLineLen);
			} else if( i%halfN == 0 && i%n != 0 ) {
				gc.fillRect(
						startX, 
						middleLineScaleY, 
						1, 
						middleLineLen);
			} else {
				gc.fillRect(
						startX, 
						longLineScaleY, 
						1, 
						longLineLen);
				
				if(showScaleText) {
					//填充数字
					String scaleText;
					if(cycleScale!=CycleMode.NONE && cycleSize > 0d) {
						scaleText = 
								scaleNumberFormat((Math.abs(i*realScaleUnit)%cycleSize));
					} else {
						scaleText = 
								scaleNumberFormat((absoluteScale?Math.abs(i*realScaleUnit):i*realScaleUnit));
					}
					gc.fillText(
							scaleText, 
							startX + 2, 
							scaleTextY);
				}
			}
			
		}
		
		//反向画坐标 循环数值判定
		for(double i = start; origin + i*realUnitLenght > absLTX; i--) {
			//判断越界
			if(origin + i*unitLenght > getWidth() - absRBX) continue;
			
			double startX = origin + i*realUnitLenght;
			if(i%halfN != 0) {
				gc.fillRect(
						startX, 
						normalLineScaleY, 
						1, 
						normalLineLen);
			} else if( i%halfN == 0 && i%n != 0 ) {
				gc.fillRect(
						startX, 
						middleLineScaleY, 
						1, 
						middleLineLen);
			} else {
				gc.fillRect(
						startX, 
						longLineScaleY, 
						1, 
						longLineLen);
				
				if(showScaleText) {
					//填充数字
					String scaleText;
					if(cycleScale!=CycleMode.NONE && cycleSize > 0d) {
						scaleText = 
								scaleNumberFormat(Math.abs(cycleSize + (i*realScaleUnit)%cycleSize)%cycleSize);
					} else {
						scaleText = 
								scaleNumberFormat((absoluteScale?Math.abs(i*realScaleUnit):i*scaleUnit[0]));
					}
					gc.fillText(
							scaleText, 
							startX + 2, 
							scaleTextY);
				}
			}
			
		}
	}
	
	//TODO
	private void drawVerticalTickMark(GraphicsContext gc) {
		//画竖线
		gc.fillRect(
				absLTX,
				absLTY,
				1, 
				getHeight() - absRBY - absLTY);
		
		gc.fillRect(
				getWidth() - absRBX - 1,
				absLTY,
				1, 
				getHeight() - absRBY - absLTY);
		
		gc.fillRect(
				absLTX,
				absLTY,
				getWidth() - absRBX - absLTX, 
				1);
		
		//TODO　虚拟数值 测试使用
		////////////////////////////////
		//cycleScale = CycleMode.ADAPTION;
		//cycleScale = CycleMode.GLOBAL;
		//cycleSize = 100.0;
		//origin = 102;
		////////////////////////////////
		
		//TODO
		double n = scaleUnit[1]/scaleUnit[0];
		double halfN = n/2;
		
		//刻度高度确定
		double longLineLen = getWidth() - absLTX - absRBX;
		double middleLineLen = longLineLen/2;
		double normalLineLen = longLineLen/4;
		//字体大小确定
		gc.setFont(Font.font(Math.min(middleLineLen,DEFAULT_SCALE_TEXT_FONT_SIZE)));
		double fontSize = gc.getFont().getSize();
		//X坐标确定
		double longLineScaleX = absLTX ;
		double normalLineScaleX = getWidth() - absRBX - normalLineLen;
		double middleLineScaleX = getWidth() - absRBX - middleLineLen;
		double scaleTextX = absLTX + (fontSize + longLineLen - normalLineLen)/2-2;
		
		//刻度数字单位大小设置
		updateULPandSNP();
		if(scaleUnit[0]==0 || unitLenght == 0) {
			System.out.println(" scaleUnit[0] 或 unitLenght 不能为0");
			return;
		}
		double realScaleUnit = scaleUnit[0]*snp;
		double realUnitLenght = unitLenght*ulp;
		
		//设置开始位置
		double start = 0;
		
		//正向画坐标 循环数值判定
		for(double i = start; origin + i*realUnitLenght < getHeight()-absRBY; i++) {
			//判断越界
			if(origin + i*realUnitLenght < absLTY) continue;
			
			double startY = origin + i*realUnitLenght;
			if(i%halfN != 0) {
				gc.fillRect(
						normalLineScaleX, 
						startY, 
						normalLineLen, 
						1);
			} else if( i%halfN == 0 && i%n != 0 ) {
				gc.fillRect( 
						middleLineScaleX, 
						startY,
						middleLineLen, 
						1);
			} else {
				gc.fillRect(
						longLineScaleX, 
						startY,
						longLineLen, 
						1);
				
				if(showScaleText) {
					//填充数字
					String scaleText;
					if(cycleScale!=CycleMode.NONE && cycleSize > 0d) {
						scaleText = 
								scaleNumberFormat((Math.abs(i*realScaleUnit)%cycleSize));
					} else {
						scaleText = 
								scaleNumberFormat((absoluteScale?Math.abs(i*realScaleUnit):i*scaleUnit[0]));
					}
					//字体旋转
					gc.save();
					gc.translate(scaleTextX - 6,startY+2);
					gc.rotate(90);
					gc.fillText(scaleText, 0, 0);
					gc.restore();
				}
			}
			
		}
		
		//反向画坐标 循环数值判定
		for(double i = start; origin + i*realUnitLenght > absLTY; i--) {
			//判断越界
			if(origin + i*unitLenght > getHeight() - absRBY) continue;
			
			double startY = origin + i*realUnitLenght;
			if(i%halfN != 0) {
				gc.fillRect(
						normalLineScaleX, 
						startY, 
						normalLineLen, 
						1);
			} else if( i%halfN == 0 && i%n != 0 ) {
				gc.fillRect( 
						middleLineScaleX, 
						startY,
						middleLineLen, 
						1);
			} else {
				gc.fillRect(
						longLineScaleX, 
						startY,
						longLineLen, 
						1);
				
				if(showScaleText) {
					//填充数字
					String scaleText;
					if(cycleScale!=CycleMode.NONE && cycleSize > 0d) {
						scaleText = 
								scaleNumberFormat(Math.abs(cycleSize + (i*realScaleUnit)%cycleSize)%cycleSize);
					} else {
						scaleText = 
								scaleNumberFormat((absoluteScale?Math.abs(i*realScaleUnit):i*realScaleUnit));
					}
					//字体旋转
					gc.save();
					gc.translate(scaleTextX - 6,startY+2);
					gc.rotate(90);
					gc.fillText(scaleText, 0, 0);
					gc.restore();
				}
			}	
		}
	}
	
	//放大倍数，坐标原点变换
	public void scaleChange(double p,double origin) {
		this.unitLenghtPower *= p;
		this.origin = origin;
		redraw();
	}
	
	//放大倍数，坐标原点变换
	public void change(double p,double origin) {
		this.unitLenghtPower = p;
		this.origin = origin;
		redraw();
	}
    /***************************************************************************
     *                                                                         *
     *                         内部监听器设计                            							*
     *                                                                         *
     **************************************************************************/
	
	//鼠标标记改变监听器
	public interface PositionChangeListener{
		public void changed(Direction dir,double oldValue,double newValue);
	}

	public void addPositionChangeListener(PositionChangeListener listener) {
		posListeners.add(listener);
	}
	
	public void removePositionChangeListener(PositionChangeListener listener) {
		posListeners.remove(listener);
	}
	
	public void removeAllPositionChangeListener() {
		posListeners.removeAll(posListeners);
	}
	
	public Iterator<PositionChangeListener> getPositionChangeListenerIterator() {
		return posListeners.iterator();
	}
	
    /***************************************************************************
     *                                                                         *
     *                         Getter和Setter                            							*
     *                                                                         *
     **************************************************************************/
	
	public Canvas getRuleCanvas() {
		return ruleCanvas;
	}

	public void setRuleCanvas(Canvas ruleCanvas) {
		this.ruleCanvas = ruleCanvas;
	}

	public Color getTickMarkColor() {
		return tickMarkColor;
	}

	public void setTickMarkColor(Color tickMarkColor) {
		this.tickMarkColor = tickMarkColor;
	}

	public Color getRuleBackgroundColor() {
		return ruleBackgroundColor;
	}

	public void setRuleBackgroundColor(Color ruleBackgroundColor) {
		this.ruleBackgroundColor = ruleBackgroundColor;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public double[] getScaleUnit() {
		return scaleUnit;
	}

	public void setScaleUnit(double[] scaleUnit) {
		this.scaleUnit = scaleUnit;
	}

	public double getOrigin() {
		return origin;
	}

	public void setOrigin(double origin) {
		this.origin = origin;
	}

	public double getAbsLTX() {
		return absLTX;
	}

	public void setAbsLTX(double absLTX) {
		this.absLTX = absLTX;
	}

	public double getAbsLTY() {
		return absLTY;
	}

	public void setAbsLTY(double absLTY) {
		this.absLTY = absLTY;
	}

	public double getUnitLenght() {
		return unitLenght;
	}

	public void setUnitLenght(double unitLenght) {
		this.unitLenght = unitLenght;
	}

	public boolean isAbsoluteScale() {
		return absoluteScale;
	}

	public void setAbsoluteScale(boolean absoluteScale) {
		this.absoluteScale = absoluteScale;
	}

	public Scene getCurrentScene() {
		return currentScene;
	}

	public void setCurrentScene(Scene currentScene) {
		changeScene(currentScene);
	}
	
	public boolean isAdaptiveLength() {
		return adaptiveLength;
	}

	public void setAdaptiveLength(boolean adaptiveLength) {
		this.adaptiveLength = adaptiveLength;
	}

	public double getUnitLenghtPower() {
		return unitLenghtPower;
	}

	public void setUnitLenghtPower(double unitLenghtPower) {
		this.unitLenghtPower = unitLenghtPower;
	}

	public double getScaleNumberPower() {
		return scaleNumberPower;
	}

	public void setScaleNumberPower(double scaleNumberPower) {
		this.scaleNumberPower = scaleNumberPower;
	}

	public boolean isLimitUnitLenght() {
		return limitUnitLenght;
	}

	public void setLimitUnitLenght(boolean limitUnitLenght) {
		this.limitUnitLenght = limitUnitLenght;
	}

	public double getMaxUnitLenght() {
		return maxUnitLenght;
	}

	public void setMaxUnitLenght(double maxUnitLenght) {
		this.maxUnitLenght = maxUnitLenght;
	}

	public double getMinUnitLenght() {
		return minUnitLenght;
	}

	public void setMinUnitLenght(double minUnitLenght) {
		this.minUnitLenght = minUnitLenght;
	}

	public double getCycleSize() {
		return cycleSize;
	}

	public void setCycleSize(double cycleSize) {
		this.cycleSize = cycleSize;
	}

	public boolean isShowScaleText() {
		return showScaleText;
	}

	public void setShowScaleText(boolean showScaleText) {
		this.showScaleText = showScaleText;
	}

	public double getAbsRBX() {
		return absRBX;
	}

	public void setAbsRBX(double absRBX) {
		this.absRBX = absRBX;
	}

	public double getAbsRBY() {
		return absRBY;
	}

	public void setAbsRBY(double absRBY) {
		this.absRBY = absRBY;
	}

	public boolean isShowMarkLine() {
		return showMarkLine;
	}

	public void setShowMarkLine(boolean showMarkLine) {
		this.showMarkLine = showMarkLine;
	}

	public Color getMarkLineColor() {
		return markLineColor;
	}

	public void setMarkLineColor(Color markLineColor) {
		this.markLineColor = markLineColor;
	}

	public CycleMode getCycleScale() {
		return cycleScale;
	}

	public void setCycleScale(CycleMode cycleScale) {
		this.cycleScale = cycleScale;
	}
}
