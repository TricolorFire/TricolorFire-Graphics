package com.tricolorfire.graphics.drawable.interfaces;

import com.tricolorfire.graphics.drawable.BrushParameters;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;

/**
 * 笔刷应用类
 *
 */
public interface IBrushEmployer extends IGraphics{

	public ObjectProperty<BrushParameters> brushParametersProperty();
	
	default BrushParameters getBrushParameters() {
		return brushParametersProperty().get();
	}
	
	default ObjectProperty<Paint> fillProperty() {
		return getBrushParameters().fillProperty();
	}

	default ObjectProperty<Paint> strokeProperty() {
		return getBrushParameters().strokeProperty();
	}

	default DoubleProperty strokeWidthProperty() {
		return getBrushParameters().strokeWidthProperty();
	}

	default ObjectProperty<StrokeType> strokeTypeProperty() {
		return getBrushParameters().strokeTypeProperty();
	}

	default DoubleProperty strokeMiterLimitProperty() {
		return getBrushParameters().strokeMiterLimitProperty();
	}

	default ObjectProperty<StrokeLineJoin> strokeLineJoinProperty() {
		return getBrushParameters().strokeLineJoinProperty();
	}

	default ObjectProperty<StrokeLineCap> strokeLineCapProperty() {
		return getBrushParameters().strokeLineCapProperty();
	}

	default DoubleProperty strokeDashOffsetProperty() {
		return getBrushParameters().strokeDashOffsetProperty();
	}
}
