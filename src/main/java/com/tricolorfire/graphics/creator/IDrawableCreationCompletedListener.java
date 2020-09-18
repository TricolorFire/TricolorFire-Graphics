package com.tricolorfire.graphics.creator;

import com.tricolorfire.graphics.drawable.interfaces.IDrawable;

/**
 * IDrawableCreationCompleted
 * <br/>Drawable 被创建完成后需要做的事情
 * <br/>一般用于组件创建完成后向 记录管理器 添加记录的
 */
public interface IDrawableCreationCompletedListener {

	public void completed(DrawableCreatorContext context,IDrawable drawable);
	
}
