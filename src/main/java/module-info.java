module com.tricolorfire.graphics {
    
    requires java.desktop;
    requires java.base;
    requires java.compiler;
    
    requires com.jfoenix;
    
    requires javafx.controls;
    requires javafx.swing;
	requires javafx.base;
    requires javafx.graphics;
    
    exports com.tricolorfire.graphics;
}