package com.tricolorfire.graphics;

import java.util.Arrays;
import java.util.Collections;

import com.jfoenix.svg.SVGGlyph;
import com.jfoenix.svg.SVGGlyphLoader;
import com.tricolorfire.graphics.util.IPropertyPlan;
import com.tricolorfire.graphics.util.PlannedDoubleProperty;

import javafx.application.Application;
import javafx.beans.property.Property;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
    	
    	Shape shape = new Rectangle(0,0,200,200);
    	//判断是否包含该区域
    	boolean s = shape.boundsInLocalProperty().get().contains(0, 0, 30, 300);
    	
    	//
    	Group group = new Group();
    	group.getChildren().add(shape);

    	Color color;
    	Paint paint;
    	LinearGradient l = new LinearGradient(0, 0, 200, 200, false, CycleMethod.REPEAT,         
    			Collections.unmodifiableList(Arrays.asList(
                new Stop(0.5, Color.RED),
                new Stop(1.0, Color.BLUE)
            ))
    			);
    	shape.setFill(l);

    	
    	IPropertyPlan<Number> plan = new IPropertyPlan<Number>() {
			
			@Override
			public <E extends Property<Number>> void plan(E property,Number oldVaule, Number newValue) {
				
			}
		};
		
		PlannedDoubleProperty valueProperty = new PlannedDoubleProperty(plan );
    	valueProperty.set(100);
    	valueProperty.set(300);
    	
    	String path = ("M918.9 540.9c17.7 0 32-14.3 32-32s-14.3-32-32-32h-96.5v-61.6h96.5c17.7 0 32-14.3 32-32s-14.3-32-32-32h-96.5V231.8c0-17.7-14.3-32-32-32H669.6V102c0-17.7-14.3-32-32-32s-32 14.3-32 32v97.8H544V102c0-17.7-14.3-32-32-32s-32 14.3-32 32v97.8h-61.6V102c0-17.7-14.3-32-32-32s-32 14.3-32 32v97.8h-42.3c-9.1 0-17.8 3.9-23.8 10.6L209.8 298c-5.3 5.9-8.2 13.5-8.2 21.4v32h-96.5c-17.7 0-32 14.3-32 32s14.3 32 32 32h96.5V477h-96.5c-17.7 0-32 14.3-32 32s14.3 32 32 32h96.5v61.6h-96.5c-17.7 0-32 14.3-32 32s14.3 32 32 32h96.5v122c0 17.7 14.3 32 32 32h120.8V922c0 17.7 14.3 32 32 32s32-14.3 32-32V820.5H480V922c0 17.7 14.3 32 32 32s32-14.3 32-32V820.5h61.6V922c0 17.7 14.3 32 32 32s32-14.3 32-32V820.5h32.8c8.5 0 16.6-3.4 22.6-9.4l88-88c6-6 9.4-14.1 9.4-22.6v-34h96.5c17.7 0 32-14.3 32-32s-14.3-32-32-32h-96.5v-61.6h96.5zM689.1 756.5H265.6V331.6l60.8-67.8h432v423.4l-69.3 69.3z");    	
    	SVGGlyph svgG = new SVGGlyph(path);
    	SVGPath svgP = new SVGPath();
    	svgP.setContent(path);
    	
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        var scene = new Scene(new StackPane(svgG), 640, 480);
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}