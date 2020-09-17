package com.tricolorfire.graphics.creator;

import com.tricolorfire.graphics.drawable.IDrawable;

/**
 * IDrawableCreationCompleted
 * <br/>Drawable 被创建完成后需要做的事情
 */
public interface IDrawableCreationCompleted {

	public void completed(IDrawable drawable) ;
	
}
