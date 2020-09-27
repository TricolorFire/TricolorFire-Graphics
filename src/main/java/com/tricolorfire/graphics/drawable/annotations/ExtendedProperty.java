package com.tricolorfire.graphics.drawable.annotations;

/**
 * 扩展属性
 * 暂时没有用
 */
public @interface ExtendedProperty {
	
	String name() default "";
	String propertyGroup() default "properties";
	
}
