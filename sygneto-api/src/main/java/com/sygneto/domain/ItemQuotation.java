package com.sygneto.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * A ItemQuotation.
 */
@Entity
@Table(name = "item_quotation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ItemQuotation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   	@Column(name = "item_quotation_id", unique = true, nullable = false)
   	private Long itemQuotationId;
    
   
    @ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "item_id")
	private Item item;
  
	 
	 @Column(name = "qunatity")
	 private float quantity;

	

	 @Column(name = "unit_price")
	 private float unitPrice;
	

	 @Column(name = "total_price")
	 private float totalPrice;


	 @Column(name = "sub_total")
	 private float subTotal;
	 
	 @Column(name = "total")
	 private float total;
	 
	 @Column(name = "sgst")
	 private float sgst;

	 @Column(name = "cgst")
	 private float cgst;

	 @Column(name = "igst")
	 private float igst;

	 @Column(name = "discount")
	 private float discount;
	      
	  @Column(name = "discounted_price")
	  private float discountedPrice;
	         
	  @ManyToOne(fetch = FetchType.EAGER)
	  @JoinColumn(name="gst_slabs")
	  private GstSlabs gstSlabs;
	
	      public float getSubTotal() {
			return subTotal;
		}

		public void setSubTotal(float subTotal) {
			this.subTotal = subTotal;
		}

		public float getTotal() {
			return total;
		}

		public void setTotal(float total) {
			this.total = total;
		}

		public GstSlabs getGstSlabs() {
			return gstSlabs;
		}

		public void setGstSlabs(GstSlabs gstSlabs) {
			this.gstSlabs = gstSlabs;
		}

	public float getSgst() {
			return sgst;
		}

		public void setSgst(float sgst) {
			this.sgst = sgst;
		}

		public float getCgst() {
			return cgst;
		}

		public void setCgst(float cgst) {
			this.cgst = cgst;
		}

		public float getIgst() {
			return igst;
		}

		public void setIgst(float igst) {
			this.igst = igst;
		}

		public float getDiscount() {
			return discount;
		}

		public void setDiscount(float discount) {
			this.discount = discount;
		}

		public float getDiscountedPrice() {
			return discountedPrice;
		}

		public void setDiscountedPrice(float discountedPrice) {
			this.discountedPrice = discountedPrice;
		}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Long getItemQuotationId() {
		return itemQuotationId;
	}

	public void setItemQuotationId(Long itemQuotationId) {
		this.itemQuotationId = itemQuotationId;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}


	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		
		this.totalPrice = totalPrice;
	}

	
}
