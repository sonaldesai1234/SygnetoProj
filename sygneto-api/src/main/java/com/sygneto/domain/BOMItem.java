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
import java.util.Set;

/**
 * A BOMItem.
 */
@Entity
@Table(name = "bom_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BOMItem extends AbstractAuditingEntity implements Serializable {

	 @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "bom_item_id", unique = true, nullable = false)
     private Long bomItemid;
	
	
	 @Column(name = "item_id")
	 private Long itemId;
    
	 @Column(name = "item_UOM")
	 private String unitOfMeasurement;
	 
	 @Column(name = "item_code")
	 private String itemCode;
	 
	 @Column(name = "quantity")
     private Long quantity;
	 
	 @Column(name = "item_name")
	    private String itemName;

	 
	public Long getBomItemid() {
		return bomItemid;
	}

	public void setBomItemid(Long bomItemid) {
		this.bomItemid = bomItemid;
	}


	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(String unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	
	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Override
	public String toString() {
		return "BOMItem [bomItemid=" + bomItemid + ", itemId=" + itemId + ", unitOfMeasurement=" + unitOfMeasurement
				+ ", itemCode=" + itemCode + ", quantity=" + quantity + "]";
	}

    
}
