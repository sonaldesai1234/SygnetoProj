package com.sygneto.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.stereotype.Component;

import com.sygneto.config.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * A BOMItem.
 */

public class SalesOrderBOM extends AbstractAuditingEntity implements Serializable {

	 private Long salesOrderId;
	
	 private String status = "active";
	 
	 private String partyName;
	 
	 private List<CustomProduct> product;

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public Long getSalesOrderId() {
		return salesOrderId;
	}

	public void setSalesOrderId(Long salesOrderId) {
		this.salesOrderId = salesOrderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<CustomProduct> getProduct() {
		return product;
	}

	public void setProduct(List<CustomProduct> product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "SalesOrderBOM [salesOrderId=" + salesOrderId + ", status=" + status + ", partyName=" + partyName
				+ ", product=" + product + "]";
	}

}