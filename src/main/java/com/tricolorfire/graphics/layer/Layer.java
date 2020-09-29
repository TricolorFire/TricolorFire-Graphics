package com.tricolorfire.graphics.layer;

import java.util.List;

import com.tricolorfire.graphics.drawable.interfaces.IDrawable;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class Layer extends Pane implements ILayer {
	
	private ObservableList<IDrawable> drawables;
	
	public Layer() {
		super();
		ObservableList<Node> nodes = super.getChildren();
		drawables = FXCollections.observableArrayList();
		drawables.addListener(new ListChangeListener<IDrawable>() {
			@Override
			public void onChanged(Change<? extends IDrawable> c) {
				while(c.next()) {
					
					//替换处理,即set(i,drawable)
					if(c.wasReplaced()) {
						
						List<? extends IDrawable> removedDrawables = c.getRemoved();
						List<? extends IDrawable> addedDrawables = c.getAddedSubList();
						int size = c.getRemovedSize();
						for(int i = 0 ; i < size ; i ++) {
							//获取位置
							int index =nodes.indexOf(removedDrawables.get(i).getNode());
							//将该位置的数据设置为替换的数据
							nodes.set(index, addedDrawables.get(i).getNode());
						}
						
						//剩余的用添加的方式
						int remainder = c.getAddedSize() - size;
						for(int i = 0 ;i < remainder ; i++) {
							nodes.add(addedDrawables.get(size + i).getNode());
						}
						
					} else
					
					//添加处理
					if(c.wasAdded()) {
						int from = c.getFrom();
						int to = c.getTo();
						List<? extends IDrawable> drawablelist = c.getList();
						for(int i = from ; i < to ; i++) {
							nodes.add(i,drawablelist.get(i).getNode());
						}
					} else 
					
					//移除处理
					if(c.wasRemoved()) {
						List<? extends IDrawable> drawablelist = c.getRemoved();
						for(IDrawable drawable : drawablelist) {
							nodes.remove(drawable.getNode());
						}
					} else 
					
					//置换处理
					if(c.wasPermutated()) {
						int size = 0;
						int permutation;
						List<? extends IDrawable> drawablelist = c.getList();
						for(int i = 0 ; i < size ; i++) {
							permutation = c.getPermutation(i);
							if(i != permutation) {
								nodes.set(i, drawablelist.get(permutation).getNode());
							}
						}
					} else
						
					//更新局部数据处理
					if(c.wasUpdated()) {
						int from = c.getFrom();
						int to = c.getTo();
						List<? extends IDrawable> drawablelist = c.getList();
						for(int i = from ; i < to ; i++) {
							nodes.set(i,drawablelist.get(i).getNode());
						}
					}
				}
			}
		});
	}
	
	@Override
	public boolean addDrawable(IDrawable drawable) {
		return drawables.add(drawable);
	}

	@Override
	public boolean addAllDrawable(IDrawable... adds) {
		return drawables.addAll(adds);
	}

	@Override
	public boolean removeDrawable(IDrawable drawable) {
		return drawables.remove(drawable);
	}

	@Override
	public void clearDrawable() {
		drawables.clear();
	}
	
	@Override
	public ObservableList<IDrawable> getDrawables() {
		return drawables;
	}
	
	/**
	 *   该方法不可用
	 *   会返回一个不可修改的列表
	 */
	@Deprecated
	@Override
	public ObservableList<Node> getChildren() {
		return super.getChildrenUnmodifiable();
	}

}
