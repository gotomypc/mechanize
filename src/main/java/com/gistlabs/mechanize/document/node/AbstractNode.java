package com.gistlabs.mechanize.document.node;

import java.util.Collections;
import java.util.List;

import com.gistlabs.mechanize.document.query.AbstractQuery;
import com.gistlabs.mechanize.util.css_query.NodeSelector;

public abstract class AbstractNode implements Node {

	@Override
	public Node get(AbstractQuery<?> query) {
		return null;
	}

	@Override
	public List<? extends Node> getAll(AbstractQuery<?> query) {
		return Collections.emptyList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Node> T find(String query) {
		return (T) buildNodeSelector().find(query);
	}
	
	@Override
	public List<? extends Node> findAll(String query) {
		return buildNodeSelector().findAll(query);
	}

	protected NodeSelector<? extends Node> buildNodeSelector() {
		return new NodeSelector<Node>(new CssNodeHelper(this), this);
	}
	
	@Override
	public void visit(NodeVisitor visitor) {
		if(visitor.beginNode(this)) {
			for(Node child : getChildren())
				child.visit(visitor);
		}
		visitor.endNode(this);
	}
	
	@Override
	public List<? extends Node> getChildren() {
		return getChildren(new String[]{});
	}
}
