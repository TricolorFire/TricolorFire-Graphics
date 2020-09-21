package com.tricolorfire.graphics;

import java.util.Arrays;
import java.util.Collections;

import com.jfoenix.svg.SVGGlyph;
import com.jfoenix.svg.SVGGlyphLoader;
import com.tricolorfire.graphics.drawable.DrawableGroup;
import com.tricolorfire.graphics.drawable.impl.EllipseDrawable;
import com.tricolorfire.graphics.drawable.impl.RectangleDrawable;
import com.tricolorfire.graphics.util.IPropertyPlan;
import com.tricolorfire.graphics.util.PlannedDoubleProperty;

import javafx.application.Application;
import javafx.beans.property.Property;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
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

    	String path = ("M918.9 540.9c17.7 0 32-14.3 32-32s-14.3-32-32-32h-96.5v-61.6h96.5c17.7 0 32-14.3 32-32s-14.3-32-32-32h-96.5V231.8c0-17.7-14.3-32-32-32H669.6V102c0-17.7-14.3-32-32-32s-32 14.3-32 32v97.8H544V102c0-17.7-14.3-32-32-32s-32 14.3-32 32v97.8h-61.6V102c0-17.7-14.3-32-32-32s-32 14.3-32 32v97.8h-42.3c-9.1 0-17.8 3.9-23.8 10.6L209.8 298c-5.3 5.9-8.2 13.5-8.2 21.4v32h-96.5c-17.7 0-32 14.3-32 32s14.3 32 32 32h96.5V477h-96.5c-17.7 0-32 14.3-32 32s14.3 32 32 32h96.5v61.6h-96.5c-17.7 0-32 14.3-32 32s14.3 32 32 32h96.5v122c0 17.7 14.3 32 32 32h120.8V922c0 17.7 14.3 32 32 32s32-14.3 32-32V820.5H480V922c0 17.7 14.3 32 32 32s32-14.3 32-32V820.5h61.6V922c0 17.7 14.3 32 32 32s32-14.3 32-32V820.5h32.8c8.5 0 16.6-3.4 22.6-9.4l88-88c6-6 9.4-14.1 9.4-22.6v-34h96.5c17.7 0 32-14.3 32-32s-14.3-32-32-32h-96.5v-61.6h96.5zM689.1 756.5H265.6V331.6l60.8-67.8h432v423.4l-69.3 69.3z");    	
    	SVGGlyph svgG = new SVGGlyph(path);
    	SVGPath svgP = new SVGPath();
    	svgP.setContent(path);
    	
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        
        FlowPane flowPane = new FlowPane();
        flowPane.getChildren().add(label);
        
        //flowPane.autosize();
        //label.autosize();
        //System.out.println(label.getWidth());
        //System.out.println(flowPane.getWidth());
        
        
        
        
        //var scene = new Scene(new StackPane(flowPane), 640, 480);
        //StackPane sp;
        Pane pane = new Pane();
        RectangleDrawable rect0 = new RectangleDrawable();
        rect0.setLocation(100, 100);
        rect0.setSize(100, 200);
        
        RectangleDrawable rect1 = new RectangleDrawable();
        rect1.setLocation(50, 50);
        rect1.setSize(50, 50);
        
        EllipseDrawable ellipse = new EllipseDrawable(30, 30, 30, 20);
        
        DrawableGroup dgroup = DrawableGroup.create(rect0,rect1,ellipse);
        
        //dgroup.setWidth(100);
        //dgroup.setHeight(200);
        
        pane.getChildren().addAll(dgroup);
        
        //pane.getChildren().addAll(rect0,rect1);
        //dgroup.autosize();
        
        pane.autosize();
        
        double x = dgroup.getLayoutX();
        double y = dgroup.getLayoutY();
        
        //double width = dgroup.getWidth();
        //double height = dgroup.getHeight();
        
        //System.out.println("x:"+ x + " y:" + y);
        //System.out.println("width:" + width + " height:" + height);

        dgroup.setFill(Color.BLUE);
        double width = pane.getWidth();
        double height = pane.getHeight();
        pane.setStyle("-fx-background-color:#000000");
        
        System.out.println("x:"+ x + " y:" + y);
        System.out.println("width:" + width + " height:" + height);
        
        System.out.println();
        
        
        
        var scene = new Scene(new Pane(pane), 640, 480);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}