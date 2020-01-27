package com.sygneto.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.sql.Date;
import java.time.Instant;

/**
 * A ProductMrp.
 */
@Entity
@Table(name = "product_mrp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductMrp extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_mrp_id")
    private Long productMrpId;

    @Column(name = "product_mrp_price")
    private float productMrpPrice;

    
    @Column(name = "valid_from")
    private Instant validFrom; 
    
     @Column(name = "valid_till")
     private Instant validTill; 
     
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="gst_slabs")
    private GstSlabs gstSlabs;
    
//  @Column(name = "gst_slab")
//	private float gstSlab;
    
    @Column(name = "discount")
    private float discount;
    
    @Column(name = "discount_unit") //in % or in Rs.
    private String discountUnit;
    
    
	@Column(name = "sgst")
	private float sgst;

	@Column(name = "cgst")
	private float cgst;

	@Column(name = "igst")
	private float igst;

    
    @Column(name = "total_product_price")
    private float totalProductPrice;
	
	public String getDiscountUnit() {
		return discountUnit;
	}


	public void setDiscountUnit(String discountUnit) {
		this.discountUnit = discountUnit;
	}


	public float getDiscount() {
		return discount;
	}


	public void setDiscount(float discount) {
		this.discount = discount;
	}


	public float getTotalProductPrice() {
		return totalProductPrice;
	}


	public void setTotalProductPrice(float totalProductPrice) {
		this.totalProductPrice = totalProductPrice;
	}


	public Long getProductMrpId() {
		return productMrpId;
	}


	public void setProductMrpId(Long productMrpId) {
		this.productMrpId = productMrpId;
	}


	public float getProductMrpPrice() {
		return productMrpPrice;
	}


	public void setProductMrpPrice(float productMrpPrice) {
		this.productMrpPrice = productMrpPrice;
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




	

}
