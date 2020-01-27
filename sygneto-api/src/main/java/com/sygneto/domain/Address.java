package com.sygneto.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * A PurchaseOrder.
 */
/**
 * @author vishwanathg
 *
 */
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Address extends AbstractAuditingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id", unique = true, nullable = false)
	private Long addressId;

	@Column(name = "state")
	private String state;

	@Column(name = "country")
	private String country;

	@Column(name = "city_name")
	private String cityName;
	
	@Column(name = "address_type")
	private String addressType;

	@Column(name = "area")
	private String area;

	@Column(name = "flat")
	private String flat;
	
	@Column(name = "land_mark")
	private String landMark;

	@Column(name = "address_line1")
	private String addressLine1;

	@Column(name = "address_line2")
	private String addressLine2;

	@Column(name = "pin_code")
	private String pinCode;
	
	/*@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "party_id")
	private Party party;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "contact_id")
	private Contact contact;*/

	/*@JsonIgnore
	 @ManyToOne
	    @JoinColumn(name="party_id", nullable=false)
	    private Party party;*/
	
	
	@JsonIgnore
	 @ManyToOne
	    @JoinColumn(name="contact_id", nullable=false)
	    private Contact contact;
	
	
	@JsonIgnore
      @ManyToOne
	    @JoinColumn(name="employee_id", nullable=false)
	    private Employees employees;
	

	public Contact getContact() {
		return contact;
	}



	public void setContact(Contact contact) {
		this.contact = contact;
	}



	public Long getAddressId() {
		return addressId;
	}

	

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getLandMark() {
		return landMark;
	}

	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}


	public Employees getEmployees() {
		return employees;
	}



	public void setEmployees(Employees employees) {
		this.employees = employees;
	}



	public String getAddressType() {
		return addressType;
	}



	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}



	public String getFlat() {
		return flat;
	}



	public void setFlat(String flat) {
		this.flat = flat;
	}



	@Override
	public String toString() {
		return "Address [addressId=" + addressId + ", state=" + state + ", country=" + country + ", cityName="
				+ cityName + ", area=" + area + ", landMark=" + landMark + ", addressLine1=" + addressLine1
				+ ", addressLine2=" + addressLine2 + ", pinCode=" + pinCode + "]";
	}

	
	
}
