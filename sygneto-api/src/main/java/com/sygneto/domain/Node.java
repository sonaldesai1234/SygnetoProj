package com.sygneto.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Node.
 */
@Entity
@Table(name = "node")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Node extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "node_id")
	private Long nodeId;

	@Column(name = "tree_id")
	private Long treeId;

	@Column(name = "node_type")
	private String nodeType;

	@Column(name = "status")
	private String status;
	
	@Column(name = "next_action")
	private Long nextAction;

	@OneToMany(mappedBy = "node", fetch = FetchType.EAGER)
	private Set<Children> children = new HashSet<>();

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "node")
	private NodeLabel label;

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not

	public Set<Children> getChildren() {
		return children;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public Node children(Set<Children> children) {
		this.children = children;
		return this;
	}

	public Node addChildren(Children children) {
		this.children.add(children);
		children.setNode(this);
		return this;
	}

	public Node removeChildren(Children children) {
		this.children.remove(children);
		children.setNode(null);
		return this;
	}

	public void setChildren(Set<Children> children) {
		this.children = children;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public NodeLabel getLabel() {
		return label;
	}

	public void setLabel(NodeLabel label) {
		this.label = label;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getTreeId() {
		return treeId;
	}

	public void setTreeId(Long treeId) {
		this.treeId = treeId;
	}

	
	public Long getNextAction() {
		return nextAction;
	}

	public void setNextAction(Long nextAction) {
		this.nextAction = nextAction;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Node node = (Node) o;
		if (node.getNodeId() == null || getNodeId() == null) {
			return false;
		}
		return Objects.equals(getNodeId(), node.getNodeId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getNodeId());
	}

	@Override
	public String toString() {
		return "Node{" + "id=" + getNodeId() + "}";
	}
}
