package com.tricolorfire.graphics;

import java.util.Arrays;
import java.util.Collections;

import javafx.application.Application;
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
    	
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        var scene = new Scene(new StackPane(shape), 640, 480);
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}