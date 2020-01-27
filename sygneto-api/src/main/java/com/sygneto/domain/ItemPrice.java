package com.sygneto.domain;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
@Table(name = "item_price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ItemPrice extends AbstractAuditingEntity implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_price_id", unique = true, nullable = false)
    private Long itemPriceId;

    @Column(name = "valid_from")
    private Instant validFrom;
    
    @Column(name = "valid_till")
    private Instant validTill;

    @Column(name = "total_item_price")
    private float totalItemPrice;

	public Long getItemPriceId() {
		return itemPriceId;
	}

	public void setItemPriceId(Long itemPriceId) {
		this.itemPriceId = itemPriceId;
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

	@Override
	public String toString() {
		return "ItemPrice [itemPriceId=" + itemPriceId + ", validFrom=" + validFrom + ", validTill=" + validTill
				+ ", totalItemPrice=" + totalItemPrice + "]";
	}
	
	
	
   /* @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "item_price_item",
        joinColumns = {@JoinColumn(name = "item_price_id", referencedColumnName = "item_price_id")},
        inverseJoinColumns = {@JoinColumn(name = "item_id", referencedColumnName = "item_id")}) //define foreign key
    //@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Item> item = new HashSet<>();*/
    
	


   

    
}
