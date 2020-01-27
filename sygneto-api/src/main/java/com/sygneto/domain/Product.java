package com.sygneto.domain;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Product extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", unique = true, nullable = false)
    private Long productId;
    
    @Column(name = "product_name")
    private String productName;
    
    @Column(name = "product_code")
    private String  productCode;
    
    @Column(name = "product_number")
    private String productNumber;
    
    @Column(name = "sac")
    private String sac;
    
    @Column(name = "sku")
    private String sku;
   
    @Column(name = "unit_of_measurment")
    private String unitOfMeasurment;
    
    @Column(name = "total_qty_in_stock")
    private Long totalQtyInStock;
    
    @Column(name = "product_specification")
    private String productSpecification;
    
    @Column(name = "product_information")
    private String productInformation;
 
    @Column(name = "base_image_url")
    private String baseImageUrl;
    
   /* @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
	private Set<Media> media = new HashSet<>();*/
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "product_media",
        joinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "product_id")},
        inverseJoinColumns = {@JoinColumn(name = "media_id", referencedColumnName = "media_id")}) //define foreign key
    //@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Media> media = new HashSet<>();
 
    @Column(name = "product_version")
    private float productVersion;

    @Column(name = "status")
    private String status="ACTIVE";
   
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "product_mrp_id")
	private ProductMrp productMrp;

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_category_id")
	private ProductCategory productCategory;
	

	public ProductMrp getProductMrp() {
		return productMrp;
	}

	public void setProductMrp(ProductMrp productMrp) {
		this.productMrp = productMrp;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
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

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<Media> getMedia() {
		return media;
	}

	public void setMedia(Set<Media> media) {
		this.media = media;
	}

	public String getSac() {
		return sac;
	}

	public void setSac(String sac) {
		this.sac = sac;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getUnitOfMeasurment() {
		return unitOfMeasurment;
	}

	public void setUnitOfMeasurment(String unitOfMeasurment) {
		this.unitOfMeasurment = unitOfMeasurment;
	}

	public Long getTotalQtyInStock() {
		return totalQtyInStock;
	}

	public void setTotalQtyInStock(Long totalQtyInStock) {
		this.totalQtyInStock = totalQtyInStock;
	}

	
	public String getProductInformation() {
		return productInformation;
	}

	public void setProductInformation(String productInformation) {
		this.productInformation = productInformation;
	}

	public String getBaseImageUrl() {
		return baseImageUrl;
	}

	public void setBaseImageUrl(String baseImageUrl) {
		this.baseImageUrl = baseImageUrl;
	}


	public float getProductVersion() {
		return productVersion;
	}

	public void setProductVersion(float productVersion) {
		this.productVersion = productVersion;
	}

	public String getProductSpecification() {
		return productSpecification;
	}

	public void setProductSpecification(String productSpecification) {
		this.productSpecification = productSpecification;
	}

	
}
