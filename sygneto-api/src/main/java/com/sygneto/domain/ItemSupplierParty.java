package com.sygneto.domain;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A item_price.
 */
@Entity
@Table(name = "item_supplier_party")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ItemSupplierParty extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_supplier_party_id", unique = true, nullable = false)
	private Long itemSupplierPartyId;

	@Column(name = "status")
	private String status="Active";

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "item_id")
	private Item item;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "party_id")
	private Party party;

    @Column(name = "item_unit_price")
	  private Long itemUnitPrice;
	      
	@Column(name = "valid_from")
	  private Instant validFrom;
	    
	 @Column(name = "valid_till")
	  private Instant validTill;
	    

		public void setParty(Party party) {
		this.party = party;
	}

		public Long getItemSupplierPartyId() {
			return itemSupplierPartyId;
		}

		public void setItemSupplierPartyId(Long itemSupplierPartyId) {
			this.itemSupplierPartyId = itemSupplierPartyId;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public Long getItemUnitPrice() {
			return itemUnitPrice;
		}

		public void setItemUnitPrice(Long itemUnitPrice) {
			this.itemUnitPrice = itemUnitPrice;
		}

		public Instant getValidFrom() {
			return validFrom;
		}

		public void setValidFrom(Instant validFrom) {
			this.validFrom = validFrom;
		}

		public Instant getValidTill() {
			return validTill;
		}

		public void setValidTill(Instant validTill) {
			this.validTill = validTill;
		}

		
		public Item getItem() {
			return item;
		}

		public void setItem(Item item) {
			this.item = item;
		}

		
		public Party getParty() {
			return party;
		}

		@Override
		public String toString() {
			return "ItemSupplierParty [itemSupplierPartyId=" + itemSupplierPartyId + ", status=" + status
					+ ", itemUnitPrice=" + itemUnitPrice + ", validFrom=" + validFrom + ", validTill=" + validTill
					+ "]";
		}
	    
	    
}
