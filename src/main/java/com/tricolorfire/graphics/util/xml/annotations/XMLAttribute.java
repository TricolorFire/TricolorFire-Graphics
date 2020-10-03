package com.tricolorfire.graphics.util.xml.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注XML字段属性
 * 只针对于基础类
 * String
 * Number
 * Char
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface XMLAttribute {
	public String name() default "";
}
