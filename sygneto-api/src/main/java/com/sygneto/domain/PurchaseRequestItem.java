package com.sygneto.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A PurchaseRequestItem.
 */
@Entity
@Table(name = "purchase_request_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PurchaseRequestItem extends AbstractAuditingEntity implements Serializable {

	
	
	
	
	
	/////////////////////not in use////////////////////
    private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_request_item_id", unique = true, nullable = false)
    private Long purchaseRequestItemId;

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

	public Long getPurchaseRequestItemId() {
		return purchaseRequestItemId;
	}

	public void setPurchaseRequestItemId(Long purchaseRequestItemId) {
		this.purchaseRequestItemId = purchaseRequestItemId;
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

	@Override
	public String toString() {
		return "PurchaseRequestItem [purchaseRequestItemId=" + purchaseRequestItemId + ", salesOrderId=" + salesOrderId
				+ ", productName=" + productName + ", itemId=" + itemId + ", unitOfMeasurement=" + unitOfMeasurement
				+ ", itemCode=" + itemCode + ", quantity=" + quantity + ", itemName=" + itemName + "]";
	}

}
