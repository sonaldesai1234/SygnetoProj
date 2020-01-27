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
 * A inward_items.
 */
@Entity
@Table(name = "inward_items")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InverdItems extends AbstractAuditingEntity implements Serializable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "inverd_item_id", unique = true, nullable = false)
     private Long inverdItemid;
	
	 @Column(name = "quantity")
     private Long quantity;
	 
	 @Column(name = "already_Received")
	    private Long alreadyReceived;

	 @Column(name = "quantity_Received")
	    private Long quantityReceived;
	 
	 @Column(name = "notes")
	    private String notes;
	 
	 @ManyToOne(fetch = FetchType.EAGER)
	 @JoinColumn(name = "item_id")
	 private Item item;
	   
	 @Column(name="purchase_request_id")
	 private Long purchaseRequestId;

	 
	 @Column(name="sales_order_id")
	 private Long salesOrderId;

	public Long getInverdItemid() {
		return inverdItemid;
	}

	public void setInverdItemid(Long inverdItemid) {
		this.inverdItemid = inverdItemid;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getAlreadyReceived() {
		return alreadyReceived;
	}

	public void setAlreadyReceived(Long alreadyReceived) {
		this.alreadyReceived = alreadyReceived;
	}

	public Long getQuantityReceived() {
		return quantityReceived;
	}

	public void setQuantityReceived(Long quantityReceived) {
		this.quantityReceived = quantityReceived;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	

	public Long getPurchaseRequestId() {
		return purchaseRequestId;
	}

	public void setPurchaseRequestId(Long purchaseRequestId) {
		this.purchaseRequestId = purchaseRequestId;
	}

	public Long getSalesOrderId() {
		return salesOrderId;
	}

	public void setSalesOrderId(Long salesOrderId) {
		this.salesOrderId = salesOrderId;
	}

	@Override
	public String toString() {
		return "InverdItems [inverdItemid=" + inverdItemid + ", quantity=" + quantity + ", alreadyReceived="
				+ alreadyReceived + ", quantityReceived=" + quantityReceived + ", notes=" + notes + ", item=" + item
				+ "]";
	}
    
}
