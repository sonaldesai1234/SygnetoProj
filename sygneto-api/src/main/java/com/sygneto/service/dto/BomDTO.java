package com.sygneto.service.dto;

import java.util.List;
import java.util.Set;

import com.sygneto.domain.Address;
import com.sygneto.domain.BOMItem;
import com.sygneto.domain.Party;
import com.sygneto.domain.Product;
import com.sygneto.domain.ProductSystemPrice;

public class BomDTO {

    private Long productBomId;
    
    
    private String unitOfMeasurment;
    
 
    private Long quantity;
    
  
    private float unitPrice;
    
  
    private float totalPrice;
    
  
    private float version;

  
	private Product product;
    
  
	private ProductSystemPrice productSystemPrice;
    
   
    private Set<BOMItem> bomItem ;


	public Long getProductBomId() {
		return productBomId;
	}


	public void setProductBomId(Long productBomId) {
		this.productBomId = productBomId;
	}


	public String getUnitOfMeasurment() {
		return unitOfMeasurment;
	}


	public void setUnitOfMeasurment(String unitOfMeasurment) {
		this.unitOfMeasurment = unitOfMeasurment;
	}


	public Long getQuantity() {
		return quantity;
	}


	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}


	public float getUnitPrice() {
		return unitPrice;
	}


	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}


	public float getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}


	public float getVersion() {
		return version;
	}


	public void setVersion(float version) {
		this.version = version;
	}


	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}


	public ProductSystemPrice getProductSystemPrice() {
		return productSystemPrice;
	}


	public void setProductSystemPrice(ProductSystemPrice productSystemPrice) {
		this.productSystemPrice = productSystemPrice;
	}


	public Set<BOMItem> getBomItem() {
		return bomItem;
	}


	public void setBomItem(Set<BOMItem> bomItem) {
		this.bomItem = bomItem;
	}


	@Override
	public String toString() {
		return "BomDTO [productBomId=" + productBomId + ", unitOfMeasurment=" + unitOfMeasurment + ", quantity="
				+ quantity + ", unitPrice=" + unitPrice + ", totalPrice=" + totalPrice + ", version=" + version
				+ ", product=" + product + ", productSystemPrice=" + productSystemPrice + ", bomItem=" + bomItem + "]";
	}
    
    

}
