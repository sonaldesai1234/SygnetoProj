package com.sygneto.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ItemOutward.
 */
@Entity
@Table(name = "item_outward")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ItemOutward extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_outward_id")
	private Long itemOutwardId;

	@Column(name = "status")
	private String status="ACTIVE";

	@Column(name = "item_quantity")
	private Long itemQuantity;

	@Column(name = "instructions")
	private String instructions;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "item_id")
	private Item item;
	
	@Column(name="purchase_request_id")
	private Long purchaseRequestId;

	@Column(name="inward_purchase_request_id")
	private Long inwardpurchaseRequestId;
	
	public Long getItemOutwardId() {
		return itemOutwardId;
	}

	public void setItemOutwardId(Long itemOutwardId) {
		this.itemOutwardId = itemOutwardId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(long itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
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

	public void setItemQuantity(Long itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public Long getInwardpurchaseRequestId() {
		return inwardpurchaseRequestId;
	}

	public void setInwardpurchaseRequestId(Long inwardpurchaseRequestId) {
		this.inwardpurchaseRequestId = inwardpurchaseRequestId;
	}

	@Override
	public String toString() {
		return "ItemOutward [itemOutwardId=" + itemOutwardId + ", status=" + status + ", itemQuantity=" + itemQuantity
				+ ", instructions=" + instructions + ", item=" + item + ", purchaseRequestId=" + purchaseRequestId
				+ ", inwardpurchaseRequestId=" + inwardpurchaseRequestId + "]";
	}

	

	

}
