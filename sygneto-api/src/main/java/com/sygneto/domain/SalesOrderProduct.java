package com.sygneto.domain;

import org.hibernate.annotations.Cache;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A SalesOrderProduct.
 */
@Entity
@Table(name = "sales_order_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SalesOrderProduct implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sales_order_product_id")
	private Long salesOrderProductId;

	@Column(name = "product_Id")
	private Long productId;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "product_code")
	private String productCode;

	@Column(name = "quantity")
	private Long quantity;

	@Column(name = "unit_of_measurment")
	private String unitOfMeasurment;

	@Column(name = "product_specification")
	private String productSpecification;

	@Column(name = "product_information")
	private String productInformation;

	@Column(name = "base_image_path")
	private String baseImagePath;

	@Column(name = "thumb_nail_path")
	private String thumbNailPath;

	@Column(name = "sac")
	private String sac;

	@Column(name = "sku")
	private String sku;

	@Column(name = "discount")
	private float discount;

	@Column(name = "sgst")
	private float sgst;

	@Column(name = "cgst")
	private float cgst;

	@Column(name = "igst")
	private float igst;

	@Column(name = "tax1")
	private float tax1;

	@Column(name = "tax2")
	private float tax2;
	
	@Column(name = "product_mrp_price")
	private float productMrpPrice;

	@Column(name = "total_product_price")
	private float totalProductPrice;
	
	@Column(name = "total_with_quantity")
	private float totalWithQty;

	@Column(name = "product_bom_id")
	private Long productBomId;

	@Column(name = "bom_version")
	private float bomVersion;

	public String getThumbNailPath() {
		return thumbNailPath;
	}

	public void setThumbNailPath(String thumbNailPath) {
		this.thumbNailPath = thumbNailPath;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public float getSgst() {
		return sgst;
	}

	public void setSgst(float sgst) {
		this.sgst = sgst;
	}

	public float getCgst() {
		return cgst;
	}

	public void setCgst(float cgst) {
		this.cgst = cgst;
	}

	public float getIgst() {
		return igst;
	}

	public void setIgst(float igst) {
		this.igst = igst;
	}

	public float getTax1() {
		return tax1;
	}

	public void setTax1(float tax1) {
		this.tax1 = tax1;
	}

	public float getTax2() {
		return tax2;
	}

	public void setTax2(float tax2) {
		this.tax2 = tax2;
	}

	public Long getSalesOrderProductId() {
		return salesOrderProductId;
	}

	public void setSalesOrderProductId(Long salesOrderProductId) {
		this.salesOrderProductId = salesOrderProductId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setTotalProductPrice(Long totalProductPrice) {
		this.totalProductPrice = totalProductPrice;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getUnitOfMeasurment() {
		return unitOfMeasurment;
	}

	public void setUnitOfMeasurment(String unitOfMeasurment) {
		this.unitOfMeasurment = unitOfMeasurment;
	}

	public String getProductSpecification() {
		return productSpecification;
	}

	public void setProductSpecification(String productSpecification) {
		this.productSpecification = productSpecification;
	}

	public String getProductInformation() {
		return productInformation;
	}

	public void setProductInformation(String productInformation) {
		this.productInformation = productInformation;
	}

	public String getBaseImagePath() {
		return baseImagePath;
	}

	public void setBaseImagePath(String baseImagePath) {
		this.baseImagePath = baseImagePath;
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



	public float getTotalProductPrice() {
		return totalProductPrice;
	}

	public void setTotalProductPrice(float totalProductPrice) {
		this.totalProductPrice = totalProductPrice;
	}

	public Long getProductBomId() {
		return productBomId;
	}

	public void setProductBomId(Long productBomId) {
		this.productBomId = productBomId;
	}

	public float getBomVersion() {
		return bomVersion;
	}

	public void setBomVersion(float bomVersion) {
		this.bomVersion = bomVersion;
	}

	public float getTotalWithQty() {
		return totalWithQty;
	}

	public void setTotalWithQty(float totalWithQty) {
		this.totalWithQty = totalWithQty;
	}

	public float getProductMrpPrice() {
		return productMrpPrice;
	}

	public void setProductMrpPrice(float productMrpPrice) {
		this.productMrpPrice = productMrpPrice;
	}

	
}
