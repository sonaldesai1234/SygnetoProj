package com.sygneto.service.dto;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sygneto.domain.Address;
import com.sygneto.domain.Party;
import com.sygneto.domain.SalesOrderProduct;

public class SalesOrderDTO {
	
    private Long salesOrderId;
	
  	private String salesOrderNumber;

    private String partyName;

	private Address shippingAddress;

	private Address billingAddress;

	private Instant shippingDate;

	private int quantity;
	
	private float subTotal;

	private float sgst;

	private float cgst;

	private float igst;

	private float tax1;

	private float tax2;

	private float shippingAmount;

	private float total;

	private String instructions;

	private String isDelivered;


	
	/*@OneToMany(mappedBy = "salesOrder", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    private List<SalesOrderProduct> salesOrderProduct ;*/
    
    private List<SalesOrderProduct> salesOrderProduct ;



	public List<SalesOrderProduct> getSalesOrderProduct() {
	return salesOrderProduct;
   }

    public void setSalesOrderProduct(List<SalesOrderProduct> salesOrderProduct)
     {
	        this.salesOrderProduct = salesOrderProduct;
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

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public Instant getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(Instant shippingDate) {
		this.shippingDate = shippingDate;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(float subTotal) {
		this.subTotal = subTotal;
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

	public float getShippingAmount() {
		return shippingAmount;
	}

	public void setShippingAmount(float shippingAmount) {
		this.shippingAmount = shippingAmount;
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

	public String getIsDelivered() {
		return isDelivered;
	}

	public void setIsDelivered(String isDelivered) {
		this.isDelivered = isDelivered;
	}


	

}
