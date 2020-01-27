package com.sygneto.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
/*
import lombok.Data;
import lombok.ToString;
*/
import javax.persistence.*;

import java.io.Serializable;

/**
 * A CategoryGroup.
 */

public class CategoryGroup  implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long productId;
    
    private String productName;
    
    private Long productQuantity;
    
    private Long itemId;
    
    private String itemName;
    
    private Long itemQuantity;
    
    private Long salesOrderId;
    
    private Long totalQty;


	public Long getProductId() {
		return productId;
	}


	public void setProductId(Long productId) {
		this.productId = productId;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public Long getProductQuantity() {
		return productQuantity;
	}


	public void setProductQuantity(Long productQuantity) {
		this.productQuantity = productQuantity;
	}


	public Long getItemId() {
		return itemId;
	}


	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}


	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public Long getItemQuantity() {
		return itemQuantity;
	}


	public void setItemQuantity(Long itemQuantity) {
		this.itemQuantity = itemQuantity;
	}


	public Long getSalesOrderId() {
		return salesOrderId;
	}


	public void setSalesOrderId(Long salesOrderId) {
		this.salesOrderId = salesOrderId;
	}


	public Long getTotalQty() {
		return totalQty;
	}


	public void setTotalQty(Long totalQty) {
		this.totalQty = totalQty;
	}


	@Override
	public String toString() {
		return "CategoryGroup [productId=" + productId + ", productName=" + productName + ", productQuantity="
				+ productQuantity + ", itemId=" + itemId + ", itemName=" + itemName + ", itemQuantity=" + itemQuantity
				+ ", salesOrderId=" + salesOrderId + ", totalQty=" + totalQty + "]";
	}


	public CategoryGroup(Long productId, String productName, Long productQuantity, Long itemId, String itemName,
			Long itemQuantity, Long salesOrderId, Long totalQty) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productQuantity = productQuantity;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemQuantity = itemQuantity;
		this.salesOrderId = salesOrderId;
		this.totalQty = totalQty;
	}

}
