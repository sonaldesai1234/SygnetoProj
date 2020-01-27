package com.sygneto.service.dto;

import java.util.List;

import com.sygneto.domain.Address;
import com.sygneto.domain.Party;
import com.sygneto.domain.SalesOrderProduct;

public class PurchaseOrderDTO {
    
	private Long purchaseOrderId;
	
	private Party companyName;

	private String shippingAddress;

	private String billingAddress;

	private Party vendorCompanyName;

	//private List<PurchaseOrderItems> purchaseOrderItems ;
	
	private float subTotal;

	private float sgst;

	private float cgst;

	private float igst;

	private float tax1;

	private float tax2;

	private float shippingAmount;

	private float total;

	private String instructions;

	private String status;

	public Long getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(Long purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	public Party getCompanyName() {
		return companyName;
	}

	public void setCompanyName(Party companyName) {
		this.companyName = companyName;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public Party getVendorCompanyName() {
		return vendorCompanyName;
	}

	public void setVendorCompanyName(Party vendorCompanyName) {
		this.vendorCompanyName = vendorCompanyName;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	
}
