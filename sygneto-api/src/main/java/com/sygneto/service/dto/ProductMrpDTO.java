package com.sygneto.service.dto;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;

import com.sygneto.domain.Product;

@Entity
@Table(name = "product_mrp_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductMrpDTO {
	
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
	   

	    @Column(name = "total_product_price")
	    private float totalProductPrice;

	    
		@ManyToOne(fetch=FetchType.EAGER)
		@JoinColumn(name = "product_id")
		private Product product;


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


		public float getTotalProductPrice() {
			return totalProductPrice;
		}


		public void setTotalProductPrice(float totalProductPrice) {
			this.totalProductPrice = totalProductPrice;
		}


		public Product getProduct() {
			return product;
		}


		public void setProduct(Product product) {
			this.product = product;
		}

		
		
}
