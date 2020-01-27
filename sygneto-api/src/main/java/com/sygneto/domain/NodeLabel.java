package com.sygneto.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A NodeLabel.
 */
@Entity
@Table(name = "node_label")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NodeLabel implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_label")
    private String label;

    @Column(name = "action")
    private String action;

    @Column(name = "jhi_role")
    private String role;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "node_id", nullable = false)
    @JsonIgnore
    private Node node;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public NodeLabel label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAction() {
        return action;
    }

    public NodeLabel action(String action) {
        this.action = action;
        return this;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getRole() {
        return role;
    }

    public NodeLabel role(String role) {
        this.role = role;
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Node getNode() {
        return node;
    }

    public NodeLabel node(Node node) {
        this.node = node;
        return this;
    }

    public void setNode(Node node) {
        this.node = node;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NodeLabel nodeLabel = (NodeLabel) o;
        if (nodeLabel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nodeLabel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NodeLabel{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", action='" + getAction() + "'" +
            ", role='" + getRole() + "'" +
            "}";
    }
}
