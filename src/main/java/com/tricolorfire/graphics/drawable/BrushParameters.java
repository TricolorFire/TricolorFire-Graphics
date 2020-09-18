package com.tricolorfire.graphics.drawable;

import java.util.HashSet;
import java.util.Set;

import com.tricolorfire.graphics.drawable.impl.RectangleDrawable;
import com.tricolorfire.graphics.drawable.interfaces.IGraphics;
import com.tricolorfire.graphics.util.IPropertyPlan;
import com.tricolorfire.graphics.util.PlannedDoubleProperty;
import com.tricolorfire.graphics.util.PlannedObjectProperty;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;

public class BrushParameters implements IGraphics {

	public static final String FILL = "fill";
	public static final String STROKE = "stroke";
	public static final String STROKE_WIDTH = "stroke width";
	public static final String STROKE_TYPE = "stroke type";
	public static final String STROKE_MITER_LIMIT = "stroke miter limit";
	public static final String STROKE_LINE_JOIN = "stroke line join";
	public static final String STROKE_LINE_CAP = "stroke line cap";
	public static final String STROKE_DASH_OFFSET = "stroke dash offset";
	
	//参数
	private ObjectProperty<Paint> fillProperty = new SimpleObjectProperty<>();
	private ObjectProperty<Paint> strokeProperty = new SimpleObjectProperty<>();
	private DoubleProperty strokeWidthProperty = new SimpleDoubleProperty();
	private ObjectProperty<StrokeType> strokeTypeProperty = new SimpleObjectProperty<>();
	private DoubleProperty strokeMiterLimitProperty = new SimpleDoubleProperty();
	private ObjectProperty<StrokeLineJoin> strokeLineJoinProperty = new SimpleObjectProperty<>();
	private ObjectProperty<StrokeLineCap> strokeLineCapProperty = new SimpleObjectProperty<>();
	private DoubleProperty strokeDashOffsetProperty = new SimpleDoubleProperty();
	
	//监听器
	private Set<IChangeListener> changeListeners ;
	
	public BrushParameters() {
		init();
	}
	
	private void init() {
		//初始化监听器
		changeListeners = new HashSet<>();
		
		//设置计划
		IPropertyPlan<Paint> paintPlan = new PlanTemplate<>();
		IPropertyPlan<Number> numberPlan = new PlanTemplate<>();
		IPropertyPlan<StrokeType> strokeTypePlan = new PlanTemplate<>();
		IPropertyPlan<StrokeLineJoin> strokeLineJoinPlan = new PlanTemplate<>();
		IPropertyPlan<StrokeLineCap> strokeLineCapPlan = new PlanTemplate<>();
		
		//给每个参数初始化并添加计划
		fillProperty = new PlannedObjectProperty<>(this, FILL, paintPlan);
		strokeProperty = new PlannedObjectProperty<>(this, STROKE, paintPlan);
		strokeWidthProperty = new PlannedDoubleProperty(this, STROKE_WIDTH, numberPlan);
		strokeTypeProperty = new PlannedObjectProperty<>(this, STROKE_TYPE, strokeTypePlan);
		strokeMiterLimitProperty = new PlannedDoubleProperty(this, STROKE_MITER_LIMIT, numberPlan);
		strokeLineJoinProperty = new PlannedObjectProperty<>(this, STROKE_LINE_JOIN, strokeLineJoinPlan);
		strokeLineCapProperty = new PlannedObjectProperty<>(this, STROKE_LINE_CAP, strokeLineCapPlan);
		strokeDashOffsetProperty = new PlannedDoubleProperty(this, STROKE_DASH_OFFSET, numberPlan);
		
		//加载初始参数
		RectangleDrawable rect = new RectangleDrawable();
		rect.loadGraphicsInfoTo(this);
	}
	
	@Override
	public ObjectProperty<Paint> fillProperty() {
		return fillProperty;
	}

	@Override
	public ObjectProperty<Paint> strokeProperty() {
		return strokeProperty;
	}

	@Override
	public DoubleProperty strokeWidthProperty() {
		return strokeWidthProperty;
	}

	@Override
	public ObjectProperty<StrokeType> strokeTypeProperty() {
		return strokeTypeProperty;
	}

	@Override
	public DoubleProperty strokeMiterLimitProperty() {
		return strokeMiterLimitProperty;
	}

	@Override
	public ObjectProperty<StrokeLineJoin> strokeLineJoinProperty() {
		return strokeLineJoinProperty;
	}

	@Override
	public ObjectProperty<StrokeLineCap> strokeLineCapProperty() {
		return strokeLineCapProperty;
	}

	@Override
	public DoubleProperty strokeDashOffsetProperty() {
		return strokeDashOffsetProperty;
	}
	
	/****************************************************************
	 *                      [ 监听器处理 ]                          * 
	 ****************************************************************/
	public void addChangeListener(IChangeListener listener){
		changeListeners.add(listener);
	}
	
	public void removeChangeListener(IChangeListener listener) {
		changeListeners.remove(listener);
	}
	
	public void removeAllChangeListener() {
		changeListeners.removeAll(changeListeners);
	}
	
	/**
	 * 笔刷参数发生改变监听器
	 */
	public interface IChangeListener {
		public void change(String paramName, Object value);
	}

	//计划模板:用于处理监听器事件的计划模板
	class PlanTemplate<T> implements IPropertyPlan<T>{
		@Override
		public <E extends Property<T>> void plan(E property) {
			for(IChangeListener listener : changeListeners) {
				listener.change( property.getName() , property.getValue());
			}
		}
	}
	
}

