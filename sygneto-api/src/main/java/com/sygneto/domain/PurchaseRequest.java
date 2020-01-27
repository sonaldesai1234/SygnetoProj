package com.sygneto.domain;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A PurchaseRequest.
 */
@Entity
@Table(name = "purchase_requests")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PurchaseRequest extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="purchase_request_id")
    private Long purchaseRequestId;
    
   @Column(name="status")
   private String status="ACTIVE";
   
   /*@Column(name="required_by")
   private String requiredBy;*/
   
     @Column(name = "sales_order_id")
	 private Long salesOrderId;
	
	 @Column(name = "product_name")
     private String productName;
	
	 @Column(name = "item_id")
	 private Long itemId;
 
	 @Column(name = "item_UOM")
	 private String unitOfMeasurement;
	 
	 @Column(name = "item_code")
	 private String itemCode;
	 
	 @Column(name = "quantity")
     private Long quantity;
	 
	 @Column(name = "item_name")
	 private String itemName;
	 
	 @Column(name = "item_in_stock")
	 private Long itemInStock;
	 
	 public Long getSalesOrderId() {
		return salesOrderId;
	}

	public void setSalesOrderId(Long salesOrderId) {
		this.salesOrderId = salesOrderId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(String unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public Long getItemInStock() {
		return itemInStock;
	}

	public void setItemInStock(Long itemInStock) {
		this.itemInStock = itemInStock;
	}

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public Long getPurchaseRequestId() {
		return purchaseRequestId;
	}

	public void setPurchaseRequestId(Long purchaseRequestId) {
		this.purchaseRequestId = purchaseRequestId;
	}

    
}
