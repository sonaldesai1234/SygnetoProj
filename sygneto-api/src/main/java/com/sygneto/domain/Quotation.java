package com.sygneto.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.repository.Query;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * A Quotation.
 */
@Entity
@Table(name = "quotation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Quotation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "quotation_id", unique = true, nullable = false)
	private Long quotationId;
    
     @Column(name = "quote_start_date")
    private Instant quoteStartDate;
      
     @Column(name = "quote_end_date")
    private Instant quoteEndDate;
     
      @Column(name = "rental_days")
      private Long rentalDays;
     
 	 @Column(name = "grand_total")
 	 private float grandTotal;
 	 

 	 @Column(name = "delivery_notes")
 	 private String deliveryNotes;
 	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="party_id", nullable=false)
    private Party party;
   
    
    @OneToMany(targetEntity = ItemQuotation.class, cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name="quotation_id" , referencedColumnName = "quotation_id")
    private List<ItemQuotation> itemQuotation;
   
    public Long getRentalDays() {
		return rentalDays;
	}


	public void setRentalDays(Long rentalDays) {
		this.rentalDays = rentalDays;
	}
	

	public String getDeliveryNotes() {
		return deliveryNotes;
	}


	public void setDeliveryNotes(String deliveryNotes) {
		this.deliveryNotes = deliveryNotes;
	}

	public Instant getQuoteStartDate() {
		return quoteStartDate;
	}


	public void setQuoteStartDate(Instant quoteStartDate) {
		this.quoteStartDate = quoteStartDate;
	}


	public Instant getQuoteEndDate() {
		return quoteEndDate;
	}


	public void setQuoteEndDate(Instant quoteEndDate) {
		this.quoteEndDate = quoteEndDate;
	}

	public float getGrandTotal() {
		return grandTotal;
	}


	public void setGrandTotal(float grandTotal) {
		this.grandTotal = grandTotal;
	}


	public Long getQuotationId() {
		return quotationId;
	}


	public void setQuotationId(Long quotationId) {
		this.quotationId = quotationId;
	}
	public Party getParty() {
		return party;
	}


	public void setParty(Party party) {
		this.party = party;
	}


	public List<ItemQuotation> getItemQuotation() {
		return itemQuotation;
	}


	public void setItemQuotation(List<ItemQuotation> itemQuotation) {
		this.itemQuotation = itemQuotation;
	}


	@Override
	public String toString() {
		return "Quotation [quotationId=" + quotationId + ", quoteStartDate=" + quoteStartDate + ", quoteEndDate="
				+ quoteEndDate + ", rentalDays=" + rentalDays + ", grandTotal=" + grandTotal + ", deliveryNotes="
				+ deliveryNotes + ", party=" + party + ", itemQuotation=" + itemQuotation + "]";
	}



    
    
}
