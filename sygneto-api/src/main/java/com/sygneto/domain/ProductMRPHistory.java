package com.sygneto.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessorOrder;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ProductMRPHistory.
 */
@Entity
@Table(name = "product_mrp_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductMRPHistory extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_mrp_history_id")
    private Long productMrpHistoryId;

    @Column(name = "product_mrp_price")
    private float productMrpPrice;

    
    @Column(name = "valid_from")
     private Instant validFrom; 
    
    
    @Column(name = "valid_till")
    private Instant validTill; 
   

    @Column(name = "total_product_price")
    private float totalProductPrice;



    @Column(name = "product_id")
    private Long productId;



	public Long getProductId() {
		return productId;
	}


	public void setProductId(Long productId) {
		this.productId = productId;
	}


	public Long getProductMrpHistoryId() {
		return productMrpHistoryId;
	}


	public void setProductMrpHistoryId(Long productMrpHistoryId) {
		this.productMrpHistoryId = productMrpHistoryId;
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


	public float getTotalProductPrice() {
		return totalProductPrice;
	}


	public void setTotalProductPrice(float totalProductPrice) {
		this.totalProductPrice = totalProductPrice;
	}


	
    
	
	

}
