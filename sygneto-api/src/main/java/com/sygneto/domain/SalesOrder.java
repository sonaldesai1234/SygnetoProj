package com.sygneto.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import java.util.List;

/**
 * A SalesOrder.
 */
@Entity
@Table(name = "sales_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SalesOrder extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sales_order_id", unique = true, nullable = false)
	private Long salesOrderId;

	/*
	 * @Column(name = "sales_order_number") private String salesOrderNumber = "" +
	 * System.currentTimeMillis();
	 */
	@Column(name = "sales_order_number")
	private String salesOrderNumber;

	@Column(name = "party_id")
	private Long partyId;

	@Column(name = "party_name")
	private String partyName;

	@Column(name = "shipping_address_id")
	private Long shipplingAddressId;

	@Column(name = "shipping_address")
	private String shippingAddress;

	@Column(name = "billing_address_id")
	private Long billingAddressId;

	@Column(name = "billing_address")
	private String billingAddress;

	@Column(name = "shipping_date")
	private Instant shippingDate=Instant.now();
	


	@Column(name = "discount") // (if promo code exits then dis=prmocode val)
	private float discount = 0.0f;

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

	@Column(name = "total_tax")
	private float totalTax;

	@Column(name = "promo_code")
	private int promoCode;
	
	@Column(name = "total_cart_price")
	private float total; // cart total

	@Column(name = "status")
	private String status;

	@Column(name = "shipping_instructions")
	private String instructions;

	@Column(name = "pr_status")
	private String prStatus = "No";

	@Column(name = "sales_version")
	private float salesVersion;

	@Column(name = "item_description")
	private String itemDescription;

	@Column(name = "notes")
	private String notes;

	@OneToMany(targetEntity = SalesOrderProduct.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "sales_order_id", referencedColumnName = "sales_order_id")
	private List<SalesOrderProduct> salesOrderProduct;

	@OneToMany(targetEntity = SOPayment.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "sales_order_id", referencedColumnName = "sales_order_id")
	private List<SOPayment> soPayment;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "shipping_details_id")
	private ShippingDetails shippingDetails;

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public ShippingDetails getShippingDetails() {
		return shippingDetails;
	}

	public void setShippingDetails(ShippingDetails shippingDetails) {
		this.shippingDetails = shippingDetails;
	}

	public String getPartyName() {
		return partyName;
	}

	public String getPrStatus() {
		return prStatus;
	}

	public void setPrStatus(String prStatus) {
		this.prStatus = prStatus;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public void setSalesOrderProduct(List<SalesOrderProduct> salesOrderProductList) {
		this.salesOrderProduct = salesOrderProductList;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public List<SalesOrderProduct> getSalesOrderProduct() {
		return salesOrderProduct;
	}

	public Long getSalesOrderId() {
		return salesOrderId;
	}

	public void setSalesOrderId(Long salesOrderId) {
		this.salesOrderId = salesOrderId;
	}

	public String getSalesOrderNumber() {
		return salesOrderNumber;
	}

	public void setSalesOrderNumber(String salesOrderNumber) {
		this.salesOrderNumber = salesOrderNumber;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String address) {
		this.shippingAddress = address;
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

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public Instant getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(Instant shippingDate) {
		this.shippingDate = shippingDate;
	}

	public Long getPartyId() {
		return partyId;
	}

	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}

	public Long getShipplingAddressId() {
		return shipplingAddressId;
	}

	public void setShipplingAddressId(Long shipplingAddressId) {
		this.shipplingAddressId = shipplingAddressId;
	}

	public Long getBillingAddressId() {
		return billingAddressId;
	}

	public void setBillingAddressId(Long billingAddressId) {
		this.billingAddressId = billingAddressId;
	}

	public float getSalesVersion() {
		return salesVersion;
	}

	public void setSalesVersion(float salesVersion) {
		this.salesVersion = salesVersion;
	}

	public List<SOPayment> getSoPayment() {
		return soPayment;
	}

	public void setSoPayment(List<SOPayment> soPayment) {
		this.soPayment = soPayment;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public float getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(float totalTax) {
		this.totalTax = totalTax;
	}

	public int getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(int promoCode) {
		this.promoCode = promoCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
