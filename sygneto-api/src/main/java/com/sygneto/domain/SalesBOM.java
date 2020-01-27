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
@Entity
@Table(name = "sales_bom")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SalesBOM extends AbstractAuditingEntity implements Serializable {

	 @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "sales_bom_id", unique = true, nullable = false)
     private Long salesBomId;
	
	 @Column(name = "sales_order_id")
	 private Long salesOrderId;
	 
	 @Column(name = "status")
	 private String status = "ACTIVE";
	 
	 @Column(name = "party_name")
	  private String partyName;
	 
	 @OneToMany(mappedBy="salesBOM", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
	    private List<SalesBOMItem> salesBOMItem;

	public Long getSalesBomId() {
		return salesBomId;
	}

	public void setSalesBomId(Long salesBomId) {
		this.salesBomId = salesBomId;
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

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public List<SalesBOMItem> getSalesBOMItem() {
		return salesBOMItem;
	}

	public void setSalesBOMItem(List<SalesBOMItem> salesBOMItem) {
		this.salesBOMItem = salesBOMItem;
	}

	@Override
	public String toString() {
		return "SalesBOM [salesBomId=" + salesBomId + ", salesOrderId=" + salesOrderId + ", status=" + status
				+ ", partyName=" + partyName + ", salesBOMItem=" + salesBOMItem + "]";
	}
	

}
