package com.sygneto.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Children.
 */
@Entity
@Table(name = "children")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Children extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "action_id")
	private Long actionId;

	@Column(name = "next_action")
	private Long nextAction;

	@JoinColumn(name = "node_id", referencedColumnName = "node_id")
	@ManyToOne(optional = false)
	@JsonIgnore
	private Node node;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "children")
	private Label label;

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not
	// remove

	public Long getActionId() {
		return actionId;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

	public Children node(Node node) {
		this.node = node;
		return this;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public Long getNextAction() {
		return nextAction;
	}

	public Node getNode() {
		return node;
	}

	public void setNextAction(Long nextAction) {
		this.nextAction = nextAction;
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Children children = (Children) o;
		if (children.getActionId() == null || getActionId() == null) {
			return false;
		}
		return Objects.equals(getActionId(), children.getActionId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getActionId());
	}

	@Override
	public String toString() {
		return "Children{" + "id=" + getActionId() + "}";
	}
}
