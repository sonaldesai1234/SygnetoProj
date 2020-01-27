package com.sygneto.domain;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.sygneto.config.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Item.
 */
@Entity
@Table(name = "item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Item extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;
  
   /* @Column(name = "parent_item_id")
    private Long parentItemId;
    */
    @Column(name = "item_code")
    private String itemCode;
      
    @Column(name = "item_name")
    private String itemName;
    
    @Column(name = "item_number")
    private String itemNumber;
    
    @Column(name = "sac")
    private String sac;
    
    @Column(name = "item_UOM")
    private String unitOfMeasurement;
    
    @Column(name = "item_specification")
    private String itemSpecification;
 
    @Column(name = "total_qty_in_stock")
    private Long totalQtyInStock;
 
    @Column(name = "min_qty_required")
    private Long minQtyRequired;
    
    @Column(name = "item_description")
    private String itemDescription;
    
    @Column(name = "image_url")
    private String  imageUrl;
    
    @Column(name = "thumb_nail_url")
    private String  thumbNailUrl;
    
    @Column(name = "file_path")
	private String filePath;
	
	@Column(name = "thumb_nail_file_path")
	private String thumbNailFilePath;
	
	 @Column(name = "item_price")
	 private float itemPrice;
	 
	 @Column(name = "unit_quantity")
	    private Long unitQuantity;
	 
   /* private String  thumpNailPath;
      private String  imagePath;*/
    
    @Column(name = "status")
    private String status;
   
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_category_id")
	private ItemCategory itemCategory;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy="item", fetch = FetchType.EAGER)
    private Set<ItemSupplierParty> itemSupplierParty;
    
  /*  @OneToMany(mappedBy = "item", fetch = FetchType.EAGER)
	private Set<Media> media = new HashSet<>();*/
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "item_media",
        joinColumns = {@JoinColumn(name = "item_id", referencedColumnName = "item_id")},
        inverseJoinColumns = {@JoinColumn(name = "media_id", referencedColumnName = "media_id")}) //define foreign key
    //@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Media> media = new HashSet<>();
    
  /*  @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_price")
	private ItemPrice itemPrice;*/

	public Set<ItemSupplierParty> getItemSupplierParty() {
		return itemSupplierParty;
	}

	public void setItemSupplierParty(Set<ItemSupplierParty> itemSupplierParty) {
		this.itemSupplierParty = itemSupplierParty;
	}

	public Long getItemId() {
		return itemId;
	}
	

	public Set<Media> getMedia() {
		return media;
	}

	public void setMedia(Set<Media> media) {
		this.media = media;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}


	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getSac() {
		return sac;
	}

	public void setSac(String sac) {
		this.sac = sac;
	}

	public String getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(String unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}

	public String getItemSpecification() {
		return itemSpecification;
	}

	public void setItemSpecification(String itemSpecification) {
		this.itemSpecification = itemSpecification;
	}

	public Long getTotalQtyInStock() {
		return totalQtyInStock;
	}

	public void setTotalQtyInStock(Long totalQtyInStock) {
		this.totalQtyInStock = totalQtyInStock;
	}

	public Long getMinQtyRequired() {
		return minQtyRequired;
	}

	public void setMinQtyRequired(Long minQtyRequired) {
		this.minQtyRequired = minQtyRequired;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ItemCategory getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(ItemCategory itemCategory) {
		this.itemCategory = itemCategory;
	}

	/*public String getThumpNailPath() {
		return Constants.SERVER_URL + thumpNailUrl;
	}*/

	/*public void setThumpNailPath(String thumpNailPath) {
		this.thumpNailPath = thumpNailPath;
	}*/

/*	public String getImagePath() {
		return Constants.SERVER_URL + imageUrl;
	}*/

	/*public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}*/

	


public float getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(float itemPrice) {
		this.itemPrice = itemPrice;
	}

	public Long getUnitQuantity() {
		return unitQuantity;
	}

	public void setUnitQuantity(Long unitQuantity) {
		this.unitQuantity = unitQuantity;
	}

	/*	public String getFilePath() {
		return Constants.SERVER_URL +imageUrl;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getThumbNailFilePath() {
		return Constants.SERVER_URL +thumpNailUrl;
	}

	public void setThumbNailFilePath(String thumbNailFilePath) {
		this.thumbNailFilePath = thumbNailFilePath;
	}
*/
	
	public String getFilePath() {
		return filePath;
	}

	public String getThumbNailUrl() {
		return thumbNailUrl;
	}

	public void setThumbNailUrl(String thumbNailUrl) {
		this.thumbNailUrl = thumbNailUrl;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getThumbNailFilePath() {
		return thumbNailFilePath;
	}

	public void setThumbNailFilePath(String thumbNailFilePath) {
		this.thumbNailFilePath = thumbNailFilePath;
	}

	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", itemCode=" + itemCode + ", itemName=" + itemName + ", itemNumber="
				+ itemNumber + ", sac=" + sac + ", unitOfMeasurement=" + unitOfMeasurement + ", itemSpecification="
				+ itemSpecification + ", totalQtyInStock=" + totalQtyInStock + ", minQtyRequired=" + minQtyRequired
				+ ", itemDescription=" + itemDescription + ", imageUrl=" + imageUrl + ", thumbNailUrl=" + thumbNailUrl
				+ ", filePath=" + filePath + ", thumbNailFilePath=" + thumbNailFilePath + ", itemPrice=" + itemPrice
				+ ", unitQuantity=" + unitQuantity + ", status=" + status + ", itemCategory=" + itemCategory
				+ ", itemSupplierParty=" + itemSupplierParty + ", media=" + media + "]";
	}
	
	
    
}