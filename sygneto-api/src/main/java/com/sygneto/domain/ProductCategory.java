package com.sygneto.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Employee.
 */
@Entity
@Table(name = "product_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductCategory extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_category_id", unique = true, nullable = false)
    private Long productCategoryId;
    
    @Column(name = "parent_product_category_id")
    private Long  parentProductCategoryId;
    
    @Column(name = "category_name")
    private String categoryName;
    
    @Column(name = "category_description")
    private String categoryDescription;

	public Long getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(Long productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public Long getParentProductCategoryId() {
		return parentProductCategoryId;
	}

	public void setParentProductCategoryId(Long parentProductCategoryId) {
		this.parentProductCategoryId = parentProductCategoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	@Override
	public String toString() {
		return "ProductCategory [productCategoryId=" + productCategoryId + ", parentProductCategoryId="
				+ parentProductCategoryId + ", categoryName=" + categoryName + ", categoryDescription="
				+ categoryDescription + "]";
	}

	

	
   
    
}
