package com.sygneto.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A PurchaseOrder.
 */
@Entity
@Table(name = "purchase_order_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PurchaseOrderItems extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_order_item_id", unique = true, nullable = false)
    private Long purchaseOrderItemId;

    @Column(name = "item_id")
    private Long itemId;
    
    @Column(name = "item_name")
    private String itemName;
    
    @Column(name = "item_description")
    private String ItemDescription;
    
    @Column(name = "item_quantity")
    private Long itemQuantity;
    
    @Column(name = "unit_of_measurment")
    private String unitOfMeasurment;
    
    @Column(name="item_unit_price")
    private float itemUnitPrice;
   
    @Column(name = "total_amount")
    private float totalAmount;
    
    @Column(name = "sgst")
    private float sgst;
    
    @Column(name = "cgst")
    private float cgst;
    
    @Column(name = "igst")
    private float igst;
    
    @Column(name="tax1")
    private float tax1;
    
    @Column(name="tax2")
    private float tax2;
    
    @Column(name = "shipping_amount")
    private float shippingAmount;
    
    @Column(name="purchase_request_id")
	private Long purchaseRequestId;

    @Column(name="sales_order_id")
    private Long salesOrderId;
    
    @Column(name = "base_image_path")
    private String baseImagePath;
    
    @Column(name = "thumb_nail_path")
    private String  thumbNailPath;
    
    @Column(name = "already_Received")
    private Long alreadyReceived;
    
	public float getItemUnitPrice() {
		return itemUnitPrice;
	}
	public void setItemUnitPrice(float itemUnitPrice) {
		this.itemUnitPrice = itemUnitPrice;
	}

	public float getTax1() {
		return tax1;
	}

	public void setTax1(float tax1) {
		this.tax1 = tax1;
	}

	
	public float getTax2() {
		return tax2;
	}

	public void setTax2(float tax2) {
		this.tax2 = tax2;
	}

	public Long getPurchaseOrderItemId() {
		return purchaseOrderItemId;
	}

	public void setPurchaseOrderItemId(Long purchaseOrderItemId) {
		this.purchaseOrderItemId = purchaseOrderItemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDescription() {
		return ItemDescription;
	}

	public void setItemDescription(String itemDescription) {
		ItemDescription = itemDescription;
	}

	public Long getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(Long itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public String getUnitOfMeasurment() {
		return unitOfMeasurment;
	}

	public void setUnitOfMeasurment(String unitOfMeasurment) {
		this.unitOfMeasurment = unitOfMeasurment;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
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

	public float getShippingAmount() {
		return shippingAmount;
	}

	public void setShippingAmount(float shippingAmount) {
		this.shippingAmount = shippingAmount;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getPurchaseRequestId() {
		return purchaseRequestId;
	}

	public void setPurchaseRequestId(Long purchaseRequestId) {
		this.purchaseRequestId = purchaseRequestId;
	}

	
	public Long getSalesOrderId() {
		return salesOrderId;
	}


	public void setSalesOrderId(Long salesOrderId) {
		this.salesOrderId = salesOrderId;
	}


	public String getBaseImagePath() {
		return baseImagePath;
	}


	public void setBaseImagePath(String baseImagePath) {
		this.baseImagePath = baseImagePath;
	}


	
	

	public String getThumbNailPath() {
		return thumbNailPath;
	}

	public void setThumbNailPath(String thumbNailPath) {
		this.thumbNailPath = thumbNailPath;
	}

	public Long getAlreadyReceived() {
		return alreadyReceived;
	}



	public void setAlreadyReceived(Long alreadyReceived) {
		this.alreadyReceived = alreadyReceived;
	}



	@Override
	public String toString() {
		return "PurchaseOrderItems [purchaseOrderItemId=" + purchaseOrderItemId + ", itemId=" + itemId + ", itemName="
				+ itemName + ", ItemDescription=" + ItemDescription + ", itemQuantity=" + itemQuantity
				+ ", unitOfMeasurment=" + unitOfMeasurment + ", itemUnitPrice=" + itemUnitPrice + ", totalAmount="
				+ totalAmount + ", sgst=" + sgst + ", cgst=" + cgst + ", igst=" + igst + ", tax1=" + tax1 + ", tax2="
				+ tax2 + ", shippingAmount=" + shippingAmount + "]";
	}

}
