package com.sygneto.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ItemPriceHistory.
 */
@Entity
@Table(name = "item_price_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ItemPriceHistory extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_price_history_id", unique = true, nullable = false)
    private Long itemPriceHistoryId;
  
    @Column(name = "item_unit_price")
	  private Long itemUnitPrice;
      
    @Column(name = "valid_from")
	  private Instant validFrom;
	    
	 @Column(name = "valid_till")
	  private Instant validTill;
    
    @Column(name = "total_item_price")
    private float totalItemPrice;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "party_id")
    private Long partyId;
    

	public Long getItemPriceHistoryId() {
		return itemPriceHistoryId;
	}


	public void setItemPriceHistoryId(Long itemPriceHistoryId) {
		this.itemPriceHistoryId = itemPriceHistoryId;
	}


	public Long getItemUnitPrice() {
		return itemUnitPrice;
	}


	public void setItemUnitPrice(Long itemUnitPrice) {
		this.itemUnitPrice = itemUnitPrice;
	}


	public Long getPartyId() {
		return partyId;
	}


	public void setPartyId(Long partyId) {
		this.partyId = partyId;
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


	public float getTotalItemPrice() {
		return totalItemPrice;
	}


	public void setTotalItemPrice(float totalItemPrice) {
		this.totalItemPrice = totalItemPrice;
	}


	public Long getItemId() {
		return itemId;
	}


	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}


	@Override
	public String toString() {
		return "ItemPriceHistory [itemPriceHistoryId=" + itemPriceHistoryId + ", itemUnitPrice=" + itemUnitPrice
				+ ", validFrom=" + validFrom + ", validTill=" + validTill + ", totalItemPrice=" + totalItemPrice
				+ ", itemId=" + itemId + ", partyId=" + partyId + "]";
	}
    
    
}
