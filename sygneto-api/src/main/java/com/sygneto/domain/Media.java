package com.sygneto.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Media.
 */
@Entity
@Table(name = "media")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Media extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "media_id",unique = true, nullable = false)
    private Long mId;
	
    @Column(name = "base_image_url")
    private String baseImageUrl;
    
    @Column(name = "image_name")
    private String imageName;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "thumb_nail_url")
    private String  thumbNailUrl;
    
    @Column(name = "base_image_path")
    private String baseImagePath;
    
    @Column(name = "thumb_nail_path")
    private String  thumbNailPath;
    
    @Column(name = "item_type")
	private String itemType;

    @Column(name = "file_name")
	private String fileName;
    
    @Column(name = "item_code")
	private String itemCode;
    
    
    
    
  /*  @JsonIgnore
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
	@ManyToOne(optional = false)
	private Product product;*/
    
   /* @JsonIgnore
    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
	@ManyToOne(optional = false)
	private Item item;*/
    
	public Long getmId() {
		return mId;
	}



	public String getItemCode() {
		return itemCode;
	}



	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}



	public void setmId(Long mId) {
		this.mId = mId;
	}

	public String getBaseImageUrl() {
		return baseImageUrl;
	}

	public void setBaseImageUrl(String baseImageUrl) {
		this.baseImageUrl = baseImageUrl;
	}

	

	public String getBaseImagePath() {
		return baseImagePath;
	}

	public void setBaseImagePath(String baseImagePath) {
		this.baseImagePath = baseImagePath;
	}

	

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getThumbNailUrl() {
		return thumbNailUrl;
	}

	public void setThumbNailUrl(String thumbNailUrl) {
		this.thumbNailUrl = thumbNailUrl;
	}

	public String getThumbNailPath() {
		return thumbNailPath;
	}

	public void setThumbNailPath(String thumbNailPath) {
		this.thumbNailPath = thumbNailPath;
	}

	
}
