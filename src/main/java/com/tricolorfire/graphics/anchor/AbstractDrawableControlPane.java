package com.tricolorfire.graphics.anchor;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import com.tricolorfire.graphics.anchor.interfaces.IAdjustmentProcessor;
import com.tricolorfire.graphics.anchor.interfaces.IDrawableControlPane;
import com.tricolorfire.graphics.drawable.interfaces.IDrawable;
import com.tricolorfire.graphics.layer.LayerPane;
import com.tricolorfire.graphics.ui.PenetrablePane;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public abstract class AbstractDrawableControlPane implements IDrawableControlPane {
	
	private Map<IAdjustmentProcessor,EventHandler<MouseEvent>> processorMap;
	
	private PenetrablePane pane;
	
	private IDrawable drawable;
	private IDrawable tmpDrawable;
	private LayerPane layerPane;
	
	protected AbstractDrawableControlPane(LayerPane layerPane, IDrawable drawable,IDrawable tmpDrawable) {
		this.layerPane = layerPane;
		this.drawable = drawable;
		this.tmpDrawable = tmpDrawable;
		
		processorMap = new Hashtable<>();
		//如果没有临时Drawable就直接构造控制其本体的控制面板
		//否则直接构造临时Drawable的控制面板
		if(tmpDrawable == null) {
			pane = createPane(layerPane, drawable);
		} else {
			pane = createPane(layerPane, tmpDrawable);
			//添加默认适配器
			addAdjustmentProcessor(new DefaultAdjustmentProcessor());
		}
	}
	
	protected abstract PenetrablePane createPane(LayerPane layerPane , IDrawable drawable);
	
	@Override
	public PenetrablePane getPane() {
		return this.pane;
	}
	
	@Override
	public void addAdjustmentProcessor(IAdjustmentProcessor processor) {
		if(processor == null) {
			throw new NullPointerException();
		}
		EventHandler<MouseEvent> handler = HandlerMaker.make(processor , layerPane ,drawable, tmpDrawable);
		getPane().addEventHandler(MouseEvent.ANY, handler);
		processorMap.put(processor, handler);
	}
	
	@Override
	public boolean removeAdjustmentProcessor(IAdjustmentProcessor processor) {
		if(processor == null) {
			throw new NullPointerException();
		}
		
		if(processorMap.containsKey(processor)) {
			EventHandler<MouseEvent> handler = processorMap.get(processor);
			getPane().removeEventHandler(MouseEvent.ANY, handler);
			processorMap.remove(processor);
			return true;
		}
		return false;
	}
	
	@Override
	public Set<IAdjustmentProcessor> getAdjustmentProcessors() {
		return processorMap.keySet();
	}
	
	@Override
	public void clearAdjustmentProcessor() {
		Collection<EventHandler<MouseEvent>> handlers = processorMap.values();
		for(EventHandler<MouseEvent> handler : handlers) {
			getPane().removeEventHandler(MouseEvent.ANY, handler);
		}
		processorMap.clear();
	}
	
	//处理器
	class DefaultAdjustmentProcessor implements IAdjustmentProcessor{
		@Override
		public void start(LayerPane layerPane, IDrawable drawable, IDrawable tmpDrawable) {}
		
		@Override
		public void finished(LayerPane layerPane, IDrawable drawable, IDrawable tmpDrawable) {
			tmpDrawable.loadBoundsInfoTo(drawable);
		}
	}
	
}
