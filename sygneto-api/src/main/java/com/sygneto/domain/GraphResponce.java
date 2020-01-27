package com.sygneto.domain;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

public class GraphResponce {

	@Autowired
	Set<GraphNode> node;

	@Autowired
	Set<GraphEdge> edge;

	public Set<GraphNode> getNode() {
		return node;
	}

	public void setNode(Set<GraphNode> node) {
		this.node = node;
	}

	public Set<GraphEdge> getEdge() {
		return edge;
	}

	public void setEdge(Set<GraphEdge> edge) {
		this.edge = edge;
	}

}
