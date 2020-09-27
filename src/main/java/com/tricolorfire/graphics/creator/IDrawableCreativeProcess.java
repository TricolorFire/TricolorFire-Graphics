package com.tricolorfire.graphics.creator;

import com.tricolorfire.graphics.drawable.interfaces.IDrawable;

import javafx.scene.Node;

/**
 * IDrawableCreator
 * </br> Drawable 的构造器
 * 
 * 执行顺序：
 *                                                                       (默认会将之前的数据进行放置)         
 * MousePress --> initTempCreate--> move -->drag --> MouseRelease? -[Y]-> tempCreate --> isCompleted? -[Y]-> completed
 *                                   ^                   |                                 |
 *                                   |                  [N]                               [N]
 *                                   |___________________|_________________________________|
 * 
 */
public interface IDrawableCreativeProcess<T extends IDrawable> {
	
	/**
	 * 默认跟随画笔参数
	 * @return
	 */
	default boolean isDefaultFollowBrushParameters() {
		return true;
	}
	
	/**
	 * 鼠标牵引
	 * 当鼠标处于任意位置时临时层的图像会发生什么变化
	 * 
	 * @param context 
	 */
	public void drag(DrawableCreatorContext context);
	
	/**
	 * 鼠标移动
	 * 当鼠标处于任意位置时临时层的图像会发生什么变化
	 * @param context 
	 */
	default void move(DrawableCreatorContext context) {}
	
	/**
	 * 撤销操作
	 * @param context 
	 */
	default void undo(DrawableCreatorContext context) {}
	
	/**
	 * 初始化 临时图层节点创建[只执行一次该方法]
	 */
	public Node initTempCreate(DrawableCreatorContext context) ;
	
	/**
	 * <b>触发条件：</b> 当鼠标左键松开(release)一次/检测到一次输入信号
	 * <br/>
	 * <b>参数解释：</b> [当前通过该方法为临时层已置入的图像列表] [检测到输入的所有x坐标] [检测到输入的所有y坐标]
	 * <br/>
	 * <b>方法介绍：</b> 对context中的列表进行的任何操作都将同步影响到临时层 
	 * <br/>
	 * <b>返回说明：</b> 返回需要加入的节点,如果返回null则不向临时层中加入Drawable
	 * <br/>
	 * <b style="color:red">注:当complete()方法返回true时: 临时层中的图像就会被销毁</b>
	 * 
	 * @param context
	 */
	default Node tempCreate(DrawableCreatorContext context) {return null;}
	
	/**
	 * <b>触发条件：</b> 当鼠标左键松开(release)一次/检测到一次输入信号
	 * <br/>
	 * <b>参数解释：</b> 将满足触发条件时的鼠标坐标压入栈中(x坐标栈,y坐标栈)。然后就会执行该方法
	 * <br/> 
	 * <b>返回说明：</b>如果认为任务已经完成就返回true，否则就返回false
	 * <br/> 
	 * <b>方法介绍：</b>通常情况下该方法是直接返回true的。但对于需要绘制折线、多边形等时需要进行判断。
	 * 
	 * @param context 
	 */
	default boolean isCompleted(DrawableCreatorContext context) {
		return true;
	}
	
	/**
	 *  TODO 返回创建完成的图像和控制器
	 * @param context
	 */
	public T create(DrawableCreatorContext context);
	
}
