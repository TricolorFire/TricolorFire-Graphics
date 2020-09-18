package com.tricolorfire.graphics.drawable;

import javafx.scene.Group;

public interface IDrawable extends IBounds,IGraphics {
	
	//获得一个Group
	public Group getGroup();
	
}
