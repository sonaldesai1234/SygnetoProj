package com.sygneto.domain;

import org.hibernate.annotations.Cache;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map.Entry;

/**
 * A SalesOrderProduct.
 */
/**
 * @author vishwanathg
 *
 */
public class CustomQueryForBOM implements Serializable {

	private Long productId;

	private float version;

	public Long getProductId() {
		return productId;
	}

	public CustomQueryForBOM(Long productId, float version) {
		super();
		this.productId = productId;
		this.version = version;
	}

	public float getVersion() {
		return version;
	}

	public void setVersion(float version) {
		this.version = version;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "CustomQueryForBOM [productId=" + productId + ", version=" + version + "]";
	}

}