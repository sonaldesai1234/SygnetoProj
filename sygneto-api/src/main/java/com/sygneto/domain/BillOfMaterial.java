package com.sygneto.domain;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A bill_of_material.
 */
@Entity
@Table(name = "bill_of_material")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BillOfMaterial extends AbstractAuditingEntity implements Serializable,Cloneable{
    private static final long serialVersionUID = 1L;
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_bom_id", unique = true, nullable = false)
    private Long productBomId;
    
    @Column(name = "version")
    private float version;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
	private Product product;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_system_price_id")
	private ProductSystemPrice productSystemPrice;
    
  /*  @OneToMany(mappedBy="billOfMaterial", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    private List<BOMItem> bomItem;*/
    
    @OneToMany(targetEntity = BOMItem.class, cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	 @JoinColumn(name="product_bom_id" , referencedColumnName = "product_bom_id")
	  private List<BOMItem> bomItem;
   
    
	public Long getProductBomId() {
		return productBomId;
	}

	public void setProductBomId(Long productBomId) {
		this.productBomId = productBomId;
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

	public List<BOMItem> getBomItem() {
		return bomItem;
	}

	public void setBomItem(List<BOMItem> bomItem) {
		this.bomItem = bomItem;
	}

	@Override
	public String toString() {
		return "BillOfMaterial [productBomId=" + productBomId + ", version=" + version + ", product=" + product
				+ ", productSystemPrice=" + productSystemPrice + ", bomItem=" + bomItem + "]";
	}

	
	/*public BillOfMaterial clone() {
		BillOfMaterial clone = new BillOfMaterial();
		clone.setQuantity(this.getQuantity());
		clone.setBomItem(this.getBomItem());
		return clone;
	}*/
  
}
