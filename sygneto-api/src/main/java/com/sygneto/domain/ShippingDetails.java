package com.sygneto.domain;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "shipping_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ShippingDetails  implements Serializable{
	private static final long serialVersionUID = 1L;

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "shipping_details_id",unique=true,nullable=false)
	    private Long  shippingDetailsId;
		
		 @Column(name = "shipping_mode")
		 private String  shippingMode;
		
		 @Column(name = "shipped_date")
		 private Instant shippedDate;
		 
		 @Column(name = "shipped_by")
		 private String  shippedBy;
		 
		 @Column(name = "shipping_cost")
		 private float shippingAmount;
		
		 @Column(name = "comments")
		 private String comments;
		
		public String getComments() {
			return comments;
		}

		public void setComments(String comments) {
			this.comments = comments;
		}

		public Instant getShippedDate() {
			return shippedDate;
		}

		public void setShippedDate(Instant shippedDate) {
			this.shippedDate = shippedDate;
		}

		public Long getShippingDetailsId() {
			return shippingDetailsId;
		}

		public void setShippingDetailsId(Long shippingDetailsId) {
			this.shippingDetailsId = shippingDetailsId;
		}

		public String getShippingMode() {
			return shippingMode;
		}

		public void setShippingMode(String shippingMode) {
			this.shippingMode = shippingMode;
		}

		

		public String getShippedBy() {
			return shippedBy;
		}

		public void setShippedBy(String shippedBy) {
			this.shippedBy = shippedBy;
		}

		public float getShippingAmount() {
			return shippingAmount;
		}

		public void setShippingAmount(float shippingAmount) {
			this.shippingAmount = shippingAmount;
		}

		 
}
