package com.sygneto.domain;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bank_details")
public class BankDetails extends AbstractAuditingEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id",unique=true,nullable=false)
    private Long  paymentId;

    @Column(name = "status")
    private String  status="Active";

    @Column(name = "payment_from")
    private String  paymentFrom;

    @Column(name = "payment_for")
    private String  paymentFor;
	
    @Column(name = "payment_mode")
    private String  paymentMode;
    
    
    @Column(name = "payment_date")
    private Instant paymentDate;
    
    @Column(name = "notes")
    private String  notes;

    @Column(name = "amount")
    private float  amount;
    
    @Column(name = "transaction_reference_id")
    private String  transactionReferenceId;

    //Bank
    @Column(name = "bank_name")
    private String  bankName;
    
    @Column(name = "account_number")
    private Long  accountNumber;
    
    @Column(name = "country")
    private String  country;
    
    @Column(name = "ifsc")
    private String  IFSCCode;
    
    @Column(name = "branch_name")
    private String  branchName;
    
    @Column(name = "account_type")
    private String  accountType;
   
	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIFSCCode() {
		return IFSCCode;
	}

	public void setIFSCCode(String iFSCCode) {
		IFSCCode = iFSCCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentFrom() {
		return paymentFrom;
	}

	public void setPaymentFrom(String paymentFrom) {
		this.paymentFrom = paymentFrom;
	}

	public String getPaymentFor() {
		return paymentFor;
	}

	public void setPaymentFor(String paymentFor) {
		this.paymentFor = paymentFor;
	}

	
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Instant getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Instant paymentDate) {
		this.paymentDate = paymentDate;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getTransactionReferenceId() {
		return transactionReferenceId;
	}

	public void setTransactionReferenceId(String transactionReferenceId) {
		this.transactionReferenceId = transactionReferenceId;
	}

	
	

}
