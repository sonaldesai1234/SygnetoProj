package com.sygneto.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A ProductSystemPrice.
 */
@Entity
@Table(name = "product_system_price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductSystemPrice extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_system_price_id", unique = true, nullable = false)
    private Long productSystemPriceId;

   
    
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    
	@Column(name = "version")
    private float version;
    
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="product_id")
	private Product product;
    
    
    public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Long getProductSystemPriceId() {
		return productSystemPriceId;
	}

	public void setProductSystemPriceId(Long productSystemPriceId) {
		this.productSystemPriceId = productSystemPriceId;
	}

	
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public float getVersion() {
		return version;
	}

	public void setVersion(float version) {
		this.version = version;
	}


    
    
}
