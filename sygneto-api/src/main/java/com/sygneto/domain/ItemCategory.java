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
@Table(name = "item_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ItemCategory extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_category_id", unique = true, nullable = false)
    private Long itemCategoryId;
    
    @Column(name = "parent_item_category_id")
    private Long  parentItemCategoryId;
    
    @Column(name = "category_name")
    private String categoryName;
    
    @Column(name = "category_description")
    private String categoryDescription;

	public Long getItemCategoryId() {
		return itemCategoryId;
	}

	public void setItemCategoryId(Long itemCategoryId) {
		this.itemCategoryId = itemCategoryId;
	}

	public Long getParentItemCategoryId() {
		return parentItemCategoryId;
	}

	public void setParentItemCategoryId(Long parentItemCategoryId) {
		this.parentItemCategoryId = parentItemCategoryId;
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
		return "item_category [itemCategoryId=" + itemCategoryId + ", parentItemCategoryId=" + parentItemCategoryId
				+ ", categoryName=" + categoryName + ", categoryDescription=" + categoryDescription + "]";
	}

	
   
    
}
