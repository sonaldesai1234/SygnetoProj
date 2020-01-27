package com.sygneto.domain;
import org.hibernate.annotations.Cache;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CacheConcurrencyStrategy;


import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

/**
 * A SalesOrderProduct.
 */

public class CustomProduct implements Serializable {

    private Long productId;
 
    private String productName;

    private Long quantity;

    private List<SalesOrderBOMItem> salesOrderBOMItem;
    
    
	 

	public List<SalesOrderBOMItem> getSalesOrderBOMItem() {
		return salesOrderBOMItem;
	}

	public void setSalesOrderBOMItem(List<SalesOrderBOMItem> salesOrderBOMItem) {
		this.salesOrderBOMItem = salesOrderBOMItem;
	}

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

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "CustomProduct [productId=" + productId + ", productName=" + productName + ", quantity=" + quantity
				+ "]";
	}

    
}
