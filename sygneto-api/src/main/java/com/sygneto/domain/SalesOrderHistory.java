package com.sygneto.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * A SalesOrderHistory.
 */
@Entity
@Table(name = "sales_order_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SalesOrderHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="sales_order_id")
    private Long salesOrderId;
    
    @Column(name = "sales_order_number")
  	private String salesOrderNumber;

    @Column(name = "party_id")
    private Long partyId;
    
    @Column(name = "party_name")
    private String partyName;
    
    @Column(name = "shipping_address_id")
	private Long shipplingAddressId;
    
	@Column(name="shipping_address")
	private String shippingAddress;
	
	@Column(name = "billing_address_id")
	private Long billingAddressId;
	 
	@Column(name = "billing_address")
	private String billingAddress;
	
	@Column(name = "shipping_date")
	private Instant shippingDate=Instant.now();
	
	@Column(name = "discount")
	private float discount=0.0f;

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

	@Column(name="total_tax")
	private float totalTax;
	
	@Column(name="promo_code")
	private String promoCode;
	/*
	@Column(name = "shipping_amount")
	private float shippingAmount;
*/
	@Column(name = "total_cart_price")
	private float total;                             //cart total

	@Column(name="status")
	private String status;
	
	@Column(name = "shipping_instructions")
	private String instructions;
	
	@Column(name = "pr_status")
	private String prStatus= "No";
	
	@Column(name = "sales_version")
     private float salesVersion;


	@Column(name = "item_description")
     private String itemDescription;

	@Column(name = "notes")
    private String notes;
	
	 @OneToMany
	 @JoinColumn(name="id" , referencedColumnName = "id")
	 private List<SalesOrderProduct> salesOrderProduct;
   
	 @OneToMany
	 @JoinColumn(name="id" , referencedColumnName = "id")
	 private List<SOPayment> soPayment;
 
	 @OneToOne
	 @JoinColumn(name = "shipping_details_id")
	 private ShippingDetails shippingDetails;

	public Long getSalesOrderId() {
		return salesOrderId;
	}


	public void setSalesOrderId(Long salesOrderId) {
		this.salesOrderId = salesOrderId;
	}


	public List<SalesOrderProduct> getSalesOrderProduct() {
		return salesOrderProduct;
	}


	public void setSalesOrderProduct(List<SalesOrderProduct> salesOrderProduct) {
		this.salesOrderProduct = salesOrderProduct;
	}


	public ShippingDetails getShippingDetails() {
		return shippingDetails;
	}


	public void setShippingDetails(ShippingDetails shippingDetails) {
		this.shippingDetails = shippingDetails;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getSalesOrderNumber() {
		return salesOrderNumber;
	}


	public void setSalesOrderNumber(String salesOrderNumber) {
		this.salesOrderNumber = salesOrderNumber;
	}


	public Long getPartyId() {
		return partyId;
	}


	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}


	public String getPartyName() {
		return partyName;
	}


	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}


	public Long getShipplingAddressId() {
		return shipplingAddressId;
	}


	public void setShipplingAddressId(Long shipplingAddressId) {
		this.shipplingAddressId = shipplingAddressId;
	}


	public String getShippingAddress() {
		return shippingAddress;
	}


	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}


	public Long getBillingAddressId() {
		return billingAddressId;
	}


	public void setBillingAddressId(Long billingAddressId) {
		this.billingAddressId = billingAddressId;
	}


	public String getBillingAddress() {
		return billingAddress;
	}


	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}


	public Instant getShippingDate() {
		return shippingDate;
	}


	public void setShippingDate(Instant shippingDate) {
		this.shippingDate = shippingDate;
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


	public float getTotalTax() {
		return totalTax;
	}


	public void setTotalTax(float totalTax) {
		this.totalTax = totalTax;
	}


	public String getPromoCode() {
		return promoCode;
	}


	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}


	public float getTotal() {
		return total;
	}


	public void setTotal(float total) {
		this.total = total;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getInstructions() {
		return instructions;
	}


	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}


	public String getPrStatus() {
		return prStatus;
	}


	public void setPrStatus(String prStatus) {
		this.prStatus = prStatus;
	}


	public float getSalesVersion() {
		return salesVersion;
	}


	public void setSalesVersion(float salesVersion) {
		this.salesVersion = salesVersion;
	}


	public String getItemDescription() {
		return itemDescription;
	}


	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}


	public String getNotes() {
		return notes;
	}


	public void setNotes(String notes) {
		this.notes = notes;
	}


	
	public List<SOPayment> getSoPayment() {
		return soPayment;
	}


	public void setSoPayment(List<SOPayment> soPayment) {
		this.soPayment = soPayment;
	}

	
}
