package com.tricolorfire.graphics.drawable;

import java.util.LinkedList;
import java.util.List;

import com.tricolorfire.graphics.drawable.interfaces.IBrushEmployer;
import com.tricolorfire.graphics.drawable.interfaces.IDrawable;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;

/**
 * 
 * 
 */
public class DrawableGroup extends Group implements IBrushEmployer,IDrawable {
	
	private static final String BRUSH_PARAMETERS = "brush parameters";
	
	private ObservableList<IDrawable> drawableList;
	
	private ObjectProperty<BrushParameters> brushParametersProperty;//笔刷参数
	private DoubleProperty widthProperty;
	private DoubleProperty heightProperty;
	
	public DrawableGroup() {
		init();
	}
	
	//初始化操作
	private void init() {

		brushParametersProperty = new SimpleObjectProperty<>(this,BRUSH_PARAMETERS,new BrushParameters());
		
		setAutoSizeChildren(true);
		
		//添加图形监听器
		initGraphicsListener();
		
		//初始化在调整Group大小时，该group的对子节点的操作
		
		//初始化DrawableChildren
		initDrawableChildren();
	}
	
	@Override
	public ObjectProperty<BrushParameters> brushParametersProperty() {
		return brushParametersProperty;
	}

	@Override
	public DoubleProperty widthProperty() {
		return widthProperty;
	}

	@Override
	public DoubleProperty heightProperty() {
		return heightProperty;
	}
	
	@Override
	public Group getNode() {
		return this;
	}

	/****************************************************************
	 *                   [ 添加图形监听器 ]                         * 
	 ****************************************************************/
	private void initGraphicsListener() {
		//添加fill变更监听器
		fillProperty().addListener(new ChangeListener<Paint>() {
			@Override
			public void changed(ObservableValue<? extends Paint> observable, Paint oldValue, Paint newValue) {
				if(drawableList.isEmpty()) return;
				for (IDrawable drawable : drawableList) {
					drawable.setFill(newValue);
				}
			}
		});
		
		//添加stroke变更监听器
		strokeProperty().addListener(new ChangeListener<Paint>() {
			@Override
			public void changed(ObservableValue<? extends Paint> observable, Paint oldValue, Paint newValue) {
				if(drawableList.isEmpty()) return;
				for (IDrawable drawable : drawableList) {
					drawable.setStroke(newValue);
				}
			}
		});
		
		//添加strokeWidth变更监听器
		strokeWidthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if(drawableList.isEmpty()) return;
				for (IDrawable drawable : drawableList) {
					drawable.setStrokeWidth((double)newValue);
				}
			}
		});
		
		//添加strokeDashOffset监听器
		strokeDashOffsetProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if(drawableList.isEmpty()) return;
				for (IDrawable drawable : drawableList) {
					drawable.setStrokeDashOffset((double)newValue);
				}
			}
		});
		
		//添加strokeMiterLimit监听器
		strokeMiterLimitProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if(drawableList.isEmpty()) return;
				for (IDrawable drawable : drawableList) {
					drawable.setStrokeMiterLimit((double)newValue);
				}
			}
		});
		
		//添加strokeLineCap监听器
		strokeLineCapProperty().addListener(new ChangeListener<StrokeLineCap>() {
			@Override
			public void changed(ObservableValue<? extends StrokeLineCap> observable, StrokeLineCap oldValue,
					StrokeLineCap newValue) {
				if(drawableList.isEmpty()) return;
				for (IDrawable drawable : drawableList) {
					drawable.setStrokeLineCap(newValue);
				}
			}
		});
		
		//添加strokeLineJoin监听器
		strokeLineJoinProperty().addListener(new ChangeListener<StrokeLineJoin>() {
			@Override
			public void changed(ObservableValue<? extends StrokeLineJoin> observable, StrokeLineJoin oldValue,
					StrokeLineJoin newValue) {
				if(drawableList.isEmpty()) return;
				for (IDrawable drawable : drawableList) {
					drawable.setStrokeLineJoin(newValue);
				}
			}
		});
		
		//添加strokeType监听器
		strokeTypeProperty().addListener(new ChangeListener<StrokeType>() {
			@Override
			public void changed(ObservableValue<? extends StrokeType> observable, StrokeType oldValue,
					StrokeType newValue) {
				if(drawableList.isEmpty()) return;
				for (IDrawable drawable : drawableList) {
					drawable.setStrokeType(newValue);
				}
			}
		});
	}
	
	
	/****************************************************************
	 *                       [ 跟随处理 ]                           * 
	 ****************************************************************/

	private void initDrawableChildren(){
		ObservableList<Node> nodeList = super.getChildren();
		LinkedList<IDrawable> innerDrawableList = new LinkedList<>();
		drawableList = FXCollections.observableList(innerDrawableList);
		
		//添加监听器，将修改的信息同步到nodeList里面
		drawableList.addListener(new ListChangeListener<IDrawable>() {
			
			//获取最小位置
			private double[] getMinPosition(List<IDrawable> drawables) {
				//计算最小的LayoutX,LayoutY
				double minX = Double.MAX_VALUE,minY = Double.MAX_VALUE;
				for(IDrawable drawable : drawables) {
					double x = drawable.getLayoutX();
					double y = drawable.getLayoutY();
					if(minX > x) {
						minX = x;
					}
					
					if(minY > y) {
						minY = y;
					}
				}
				return new double[] {minX,minY};
			}
			
			//获取最小值
			private double min(double a,double b) {
				return a<b?a:b;
			}
			
			@Override
			public void onChanged(Change<? extends IDrawable> c) {
				//绑定同步
				while (c.next()) {
					
					//如果是添加
					if(c.wasAdded()) {
						
						@SuppressWarnings("unchecked")
						List<IDrawable> addedList = (List<IDrawable>) c.getAddedSubList();
						
						//获取minX minY
						double minAdded[] = getMinPosition(addedList);
						double minNow[] = getMinPosition(drawableList);
						
						double minX = min(minAdded[0],minNow[0]);
						double minY = min(minAdded[1],minNow[1]);
						
						//将数据放入Group中
						for(IDrawable drawable : addedList) {
							nodeList.add(drawable.getNode());
							drawable.setLayoutX(drawable.getLayoutX() - minX);
							drawable.setLayoutY(drawable.getLayoutY() - minY);
						}
						
						//设置Group位置
						setLayoutX(minX);
						setLayoutY(minY);
						
					}
					
					//如果是移除,不做处理
					if(c.wasRemoved()) {
						
					}
				} 
			}
			
		});
	}
	
	
	protected ObservableList<IDrawable> getDrawableChildren() {
		return drawableList;
	}
	
	/**
	 * 组合
	 * <br/>对于外部来说的显式操作
	 */
	public void combine(IDrawable...drawables) {
		drawableList.addAll(drawables);
	}
	
	/**
	 * 拆散
	 * <br/>对于外部来说的显式操作
	 */
	public IDrawable[] breakup() {
		if(drawableList.isEmpty()) {
			return null;
		}
		IDrawable[] ret = new IDrawable[drawableList.size()];
		ret = drawableList.toArray(ret);
		
		//清空节点
		drawableList.clear();
		super.getChildren().clear();
		
		//设置位置
		double dx = getLayoutX();
		double dy = getLayoutY();
		for(IDrawable drawable : ret) {
			drawable.setLayoutX(dx + drawable.getLayoutX());
			drawable.setLayoutY(dy + drawable.getLayoutY());
		}
		Parent parent = getParent();
		if(parent instanceof Pane) {
			List<Node> childrens = ((Pane)parent).getChildren();
			for(IDrawable drawable : ret) {
				childrens.add(drawable.getNode());
			}
		}
		return ret;
	}
	
	/**
	 * 该方法被废弃,调用该方法将返回ChildrenUnmodifiable,如果需要获取子节点请继承该类并调用 getDrawableChildren()
	 * @return ChildrenUnmodifiable
	 */
	
	@Override
	@Deprecated
	public ObservableList<Node> getChildren() {
		return super.getChildrenUnmodifiable();
	}
	
	@Override
	public DrawableType getType() {
		return DrawableType.GROUP;
	}
	
}
