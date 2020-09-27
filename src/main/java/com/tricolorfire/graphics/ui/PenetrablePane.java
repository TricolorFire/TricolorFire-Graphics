package com.tricolorfire.graphics.ui;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;

/**
 * PenetrablePane
 * 可穿透面板 : 鼠标监听可以穿透本面板，但本面板的组件依然可以监听鼠标行为
 */
public class PenetrablePane extends Parent{
	
	private static final double USE_COMPUTED_SIZE = -1;
	
	/********************************************************
	 *                                                      *
	 *                        prefHeight                    *
	 *                                                      *
	 ********************************************************/
	private DoubleProperty heightProperty = new SimpleDoubleProperty(PenetrablePane.this,"height");
    public final DoubleProperty heightProperty() {
        return heightProperty;
    }
    
    public final double getHeight() { 
		return heightProperty().get(); 
	}

    public void setHeight(double height) {
    	heightProperty().set(height);
    }
	
	/********************************************************
	 *                                                      *
	 *                         width                        *
	 *                                                      *
	 ********************************************************/
    
    private DoubleProperty widthProperty = new SimpleDoubleProperty(PenetrablePane.this,"width");
    
    public final DoubleProperty widthProperty() {
        return widthProperty;
    }
    
	public final double getWidth() { 
		return widthProperty().get(); 
	}
	
	public void setWidth(double width) { 
		widthProperty().set(width); 
	}
	
    /**
     * Called during layout to determine the preferred width for this node.
     * Returns the value from <code>computePrefWidth(forHeight)</code> unless
     * the application overrode the preferred width by setting the prefWidth property.
     *
     * @see #setPrefWidth
     * @return the preferred width that this node should be resized to during layout
     */
    @Override public final double prefWidth(double height) {
        final double override = getWidth();
        if (override == USE_COMPUTED_SIZE) {
            return super.prefWidth(height);
        }
        return Double.isNaN(override) || override < 0 ? 0 : override;
    }
    
    /**
     * Called during layout to determine the preferred height for this node.
     * Returns the value from <code>computePrefHeight(forWidth)</code> unless
     * the application overrode the preferred height by setting the prefHeight property.
     *
     * @see #setPrefHeight
     * @return the preferred height that this node should be resized to during layout
     */
    @Override public final double prefHeight(double width) {
        final double override = getHeight();
        if (override == USE_COMPUTED_SIZE) {
            return super.prefHeight(width);
        }
        return Double.isNaN(override) || override < 0 ? 0 : override;
    }
    
	@Override public ObservableList<Node> getChildren() {
        return super.getChildren();
	}
	
}
