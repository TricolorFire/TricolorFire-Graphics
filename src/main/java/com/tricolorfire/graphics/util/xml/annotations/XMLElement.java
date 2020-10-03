package com.tricolorfire.graphics.util.xml.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  标注可以转换为XML的类
 * name 属性为元素名,默认为类名
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface XMLElement {
	public String name() default "";
}
