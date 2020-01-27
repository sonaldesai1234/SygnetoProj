package com.sygneto.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A SOPayment.
 */
@Entity
@Table(name = "so_payment")
public class SOPayment extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id",unique=true,nullable=false)
    private Long  paymentId;

    @Column(name = "status")
    private String  status="ACTIVE";

    @Column(name = "payment_from")
    private String  paymentFrom;

    @Column(name = "payment_for")
    private String  paymentFor;
	
    @Column(name = "type")
    private String  type;
    
    @Column(name = "bank_name")
    private String  bankName;
    
    @Column(name = "payment_date")
    private Instant  paymentDate;

    @Column(name = "amount")
    private float  amount;


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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	@Override
	public String toString() {
		return "SOPayment [paymentId=" + paymentId + ", status=" + status + ", paymentFrom=" + paymentFrom
				+ ", paymentFor=" + paymentFor + ", type=" + type + ", bankName=" + bankName + ", paymentDate="
				+ paymentDate + ", amount=" + amount + "]";
	}
    
    
}
