package com.tricolorfire.graphics.anchor;

import java.util.List;

import com.tricolorfire.graphics.drawable.interfaces.IDrawable;

import javafx.scene.Node;

public class ControlPaneBean {
	
	private Long id;
	private IDrawable drawable;
	private List<Node> nodes;
	
	public ControlPaneBean(IDrawable drawable, List<Node> nodes) {
		this.drawable = drawable;
		this.nodes = nodes;
	}
	
	public ControlPaneBean(Long id, IDrawable drawable, List<Node> nodes) {
		this.id = id;
		this.drawable = drawable;
		this.nodes = nodes;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public IDrawable getDrawable() {
		return drawable;
	}
	
	public List<Node> getNodes() {
		return nodes;
	}

	@Override
	public String toString() {
		return "ControlPaneBean [id=" + id + ", drawable=" + drawable + ", nodes=" + nodes + "]";
	}
	
}
