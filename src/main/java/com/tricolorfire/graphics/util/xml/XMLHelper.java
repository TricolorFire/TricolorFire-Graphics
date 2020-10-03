package com.tricolorfire.graphics.util.xml;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.tricolorfire.graphics.util.xml.annotations.XMLAttribute;
import com.tricolorfire.graphics.util.xml.annotations.XMLElement;
import com.tricolorfire.graphics.util.xml.annotations.XMLSubElement;

public class XMLHelper {
	
	public static String shortClassName(Class<?> clazz) {
		String[] strs = clazz.getName().split("\\.");
		return strs[strs.length-1];
	}
	
	public static String getterName(Field field) {
		String name = field.getName();
		String frist = name.substring(0, 1).toUpperCase();
		return "get" + frist + name.substring(1);
	}
	
	public static String setterName(Field field) {
		String name = field.getName();
		String frist = name.substring(0, 1).toUpperCase();
		return "set" + frist + name.substring(1);
	}
	/**
	 * 
	 * @param obj
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static Element transform(Object obj) 
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		//注解表初始化
		List<Field> attributeFields = new ArrayList<Field>();
		List<Field> subElementFields = new ArrayList<Field>();
		
		//检查注解
		Class<?> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		
		for(Field field : fields) {
			if(field.isAnnotationPresent(XMLAttribute.class)) {
				attributeFields.add(field);
			}
			if(field.isAnnotationPresent(XMLSubElement.class)) {
				subElementFields.add(field);
			}
		}
		
		//设置字段
		String elementName;
		if(clazz.isAnnotationPresent(XMLElement.class)) {
			String ename = clazz.getAnnotation(XMLElement.class).name();
			if(!ename.isEmpty()) {
				elementName = ename;
			} else {
				elementName = shortClassName(clazz);
			}
		} else {
			elementName = shortClassName(clazz);
		}
		
		//创建元素
		Element element = DocumentHelper.createElement(elementName);
		
		//添加属性
		for(Field field : attributeFields) {
			
			//属性名获取
			String attrName = field.getAnnotation(XMLAttribute.class).name();
			if(attrName.isEmpty()) {
				attrName = field.getName();
			}
			
			//属性数据
			Method getter =clazz.getDeclaredMethod(getterName(field));
			Object data = getter.invoke(obj);
			
			//添加属性
			if(data != null) {
				element.addAttribute(attrName, data.toString());
			}
		}
		
		//添加子节点
		for(Field field : subElementFields) {
			
			//子节点名获取
			String subName = field.getAnnotation(XMLSubElement.class).name();
			if(subName.isEmpty()) {
				subName = field.getName();
			}
			
			//属性数据
			Method getter = clazz.getDeclaredMethod(getterName(field));
			Object data = getter.invoke(obj);
			
			if(data != null) {
				//递归获取subElement
				Element subElement = transform(data);
				//设置名称
				subElement.setName(subName);
				//加入根Element
				element.add(subElement);
			}
		}
		return element;
	}
	
	/**
	 * 
	 * @param <T>
	 * @param element
	 * @param clazz
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	public static <T> T load(Element element,Class<T> clazz) 
			throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		
		//注解表初始化
		List<Field> attributeFields = new ArrayList<Field>();
		List<Field> subElementFields = new ArrayList<Field>();
		
		//检查注解
		Field[] fields = clazz.getDeclaredFields();
		
		for(Field field : fields) {
			if(field.isAnnotationPresent(XMLAttribute.class)) {
				attributeFields.add(field);
			}
			if(field.isAnnotationPresent(XMLSubElement.class)) {
				subElementFields.add(field);
			}
		}
		
		//创建一个对象
		@SuppressWarnings("deprecation")
		Object obj = clazz.newInstance();
		
		//读取属性数据
		for(Field field : attributeFields) {
			
			//属性名获取
			String attrName = field.getAnnotation(XMLAttribute.class).name();
			if(attrName.isEmpty()) {
				attrName = field.getName();
			}
			
			//设置数据
			Class<?> type = field.getType();
			
			//原始数据
			String str = element.attributeValue(attrName);
			if(str != null && !str.isEmpty()) {
				
				//setter方法
				Method setter = clazz.getDeclaredMethod(setterName(field),field.getType());
				
				if(Number.class.isAssignableFrom(type)) {
					Method m = type.getMethod("valueOf", String.class);
					setter.invoke(obj,type.cast(m.invoke(type, str)));
				} else if(String.class.isAssignableFrom(type)){
					setter.invoke(obj,str);
				} else if(Boolean.class.isAssignableFrom(type) || boolean.class.isAssignableFrom(type)){
					setter.invoke(obj,Boolean.parseBoolean(str));
				} else {
					double value = Double.valueOf(str).doubleValue(); 
					if(type.equals(int.class)) {
						setter.invoke(obj,(int)value);
					} else if(type.equals(short.class)) {
						setter.invoke(obj,(short)value);
					} else if(type.equals(byte.class)) {
						setter.invoke(obj,(byte)value);
					} else if(type.equals(char.class)) {
						setter.invoke(obj,(char)value);
					} else if(type.equals(long.class)) {
						setter.invoke(obj,(long)value);
					} else if(type.equals(float.class)) {
						setter.invoke(obj,(float)value);
					} else if(type.equals(double.class)) {
						setter.invoke(obj,(double)value);
					}
				}
			}
		}
		
		//读取子节点数据
		for(Field field : subElementFields) {
			
			//子节点名获取
			String subName = field.getAnnotation(XMLSubElement.class).name();
			if(subName.isEmpty()) {
				subName = field.getName();
			}
			
			//属性数据
			Element subElement = element.element(subName);

			//递归获取子节点数据
			if(subElement != null) {
				Class<?> type = field.getType();
				Object subObj = load(subElement,type);
				Method setter = clazz.getDeclaredMethod(setterName(field),type);
				setter.invoke(obj, type.cast(subObj));
			}
		}
		
		return clazz.cast(obj);
	}
}

