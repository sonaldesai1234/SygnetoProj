package com.sygneto.service.dto;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.sygneto.domain.BOMItem;
import com.sygneto.domain.Product;
import com.sygneto.domain.ProductSystemPrice;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A bill_of_material.
 */

public class BillOfMaterial implements Serializable {
    private static final long serialVersionUID = 1L;
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_bom_id", unique = true, nullable = false)
    private Long productBomId;
    
    @Column(name = "unit_of_measurement")
    private String unitOfMeasurment;
    
    @Column(name = "quantity")
    private Long quantity;
    
    @Column(name = "unit_price")
    private float unitPrice;
    
    @Column(name = "total_price")
    private float totalPrice;
    
    @Column(name = "version")
    private float version;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
	private Product product;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_system_price_id")
	private ProductSystemPrice productSystemPrice;
    
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "bom_item_bom",
        joinColumns = {@JoinColumn(name = "product_bom_id", referencedColumnName = "product_bom_id")},
        inverseJoinColumns = {@JoinColumn(name = "bom_item_id", referencedColumnName = "bom_item_id")}) //define foreign key
    //@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<BOMItem> bomItem = new HashSet<>();
    
    
   /* @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
	private List<Item> item;*/
    
   /* @OneToMany(mappedBy="billOfMaterial")
    private Set<Item> item;*/
    
   /* @OneToMany(mappedBy="billOfMaterial")
    private Set<BOMItem> BOMItem;*/
    
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

	
	

	
  
}
