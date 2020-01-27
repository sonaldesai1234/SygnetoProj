package com.sygneto.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Label.
 */
@Entity
@Table(name = "label")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Label implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "label_id")
	private Long labelId;

	@Column(name = "sygneto_key")
	private String key;

	@Column(name = "sygneto_condition")
	private String condition;

	@Column(name = "sygneto_value")
	private Integer value;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "action_id", nullable = false)
	@JsonIgnore
	private Children children;

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not
	// remove

	public String getKey() {
		return key;
	}

	public Long getLabelId() {
		return labelId;
	}

	public void setLabelId(Long labelId) {
		this.labelId = labelId;
	}

	public Label key(String key) {
		this.key = key;
		return this;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCondition() {
		return condition;
	}

	public Label condition(String condition) {
		this.condition = condition;
		return this;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Integer getValue() {
		return value;
	}

	public Label value(Integer value) {
		this.value = value;
		return this;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Children getChildren() {
		return children;
	}

	public void setChildren(Children children) {
		this.children = children;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Label label = (Label) o;
		if (label.getLabelId() == null || getLabelId() == null) {
			return false;
		}
		return Objects.equals(getLabelId(), label.getLabelId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getLabelId());
	}

	@Override
	public String toString() {
		return "Label{" + "id=" + getLabelId() + ", key='" + getKey() + "'" + ", condition='" + getCondition() + "'"
				+ ", value=" + getValue() + "}";
	}
}
