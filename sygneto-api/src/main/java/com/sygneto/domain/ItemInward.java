package com.sygneto.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * A ItemInward.
 */
@Entity
@Table(name = "item_inward")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ItemInward extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_inward_id")
	private Long itemInwardId;

	@Column(name = "status", nullable = false)
	private String status = "ACTIVE";

	@Column(name = "entry_date")
	private Instant entryDate;

	@Column(name = "received_by")
	private String receivedBy;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "purchase_order_id")
	private PurchaseOrder purchaseOrder;

	/* @OneToMany(mappedBy="itemInward", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
	  private List<InverdItems> inverdItems;*/
	 
	 @OneToMany(targetEntity = InverdItems.class, cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	 @JoinColumn(name="item_inward_id" , referencedColumnName = "item_inward_id")
	  private List<InverdItems> inverdItems;

	public Long getItemInwardId() {
		return itemInwardId;
	}

	public void setItemInwardId(Long itemInwardId) {
		this.itemInwardId = itemInwardId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Instant getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Instant entryDate) {
		this.entryDate = entryDate;
	}

	public String getReceivedBy() {
		return receivedBy;
	}

	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public List<InverdItems> getInverdItems() {
		return inverdItems;
	}

	public void setInverdItems(List<InverdItems> inverdItems) {
		this.inverdItems = inverdItems;
	}


	@Override
	public String toString() {
		return "ItemInward [itemInwardId=" + itemInwardId + ", status=" + status + ", entryDate=" + entryDate
				+ ", receivedBy=" + receivedBy + ", purchaseOrder=" + purchaseOrder + ", inverdItems=" + inverdItems
				+ "]";
	}

	 
}
