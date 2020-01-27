package com.sygneto.domain;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A Contact.
 */
@Entity
@Table(name = "contact")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Contact extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contact_id")
	private Long contactId;
	
	@Column(name = "first_name")
    private String firstName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "designation")
	private String designation;

	@Column(name = "email_id")
	private String emailId;

	@Column(name = "alternate_email_id")
	private String alternateEmailId;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "alternate_mobile_number")
	private String alternateMobileNumber;

	@Column(name = "date_of_birth")
	private Instant dateOfBirth;

	@Column(name = "gender")
	private String gender;

	@Column(name = "region_code")
	private String regionCode;

	@Column(name = "privacy_option")
	private String privacyOption;

	@Column(name = "status")
	private String status;

	@Column(name = "fax_number")
	private String faxNumber;

	@Column(name = "message")
	private String message;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "party_id")
	private Party party;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "contact_type")
	private ContactType contactType;

	@OneToMany(mappedBy="contact", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    private List<Address> address;
	
	
	/*@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "contact_address",
        joinColumns = {@JoinColumn(name = "contact_id", referencedColumnName = "contact_id")},
        inverseJoinColumns = {@JoinColumn(name = "address_id", referencedColumnName = "address_id")})
    @BatchSize(size = 20)
    private Set<Address> address = new HashSet<>();
	*/
	
	public Party getParty() {
		return party;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	public ContactType getContactType() {
		return contactType;
	}

	public void setContactType(ContactType contactType) {
		this.contactType = contactType;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	
	public Long getContactId() {
		return contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getAlternateEmailId() {
		return alternateEmailId;
	}

	public void setAlternateEmailId(String alternateEmailId) {
		this.alternateEmailId = alternateEmailId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAlternateMobileNumber() {
		return alternateMobileNumber;
	}

	public void setAlternateMobileNumber(String alternateMobileNumber) {
		this.alternateMobileNumber = alternateMobileNumber;
	}

	public Instant getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Instant dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getPrivacyOption() {
		return privacyOption;
	}

	public void setPrivacyOption(String privacyOption) {
		this.privacyOption = privacyOption;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	

}
