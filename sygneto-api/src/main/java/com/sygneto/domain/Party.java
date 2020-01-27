package com.sygneto.domain;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Party.
 */
@Entity
@Table(name = "party")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Party extends AbstractAuditingEntity implements Serializable {

  
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_id", unique = true, nullable = false)
    private Long partyId;
    
    @Column(name = "party_name", unique = true, nullable = false)
    private String partyName;
    
    @Column(name = "party_information")
    private String partyInformation;
   
	@Column(name = "employee_quantity")
    private Long employeeQuantity;
    
 
    @Column(name = "party_email_id")
    private String partyEmailId;
  
    @Column(name = "party_fax_number")
    private String  partyFaxNumber;
  
	@Column(name = "party_mobile_number")
    private String partyMobileNumber;
    
    @Column(name = "party_PAN")
    private String partyPAN;
    
    @Column(name = "party_GSTIN")
    private String partyGSTIN;
     
    @Column(name = "party_Udyog_aadhaar")
    private String partyUdyogAadhaar; 
    
    @Column(name = "is_supplier")
    private String isSupplier; 
     
    @Column(name = "is_customer")
    private String isCustomer; 
    
    @Column(name = "is_Organisation")
    private String isOrganisation; 
    
    @Column(name = "image_url")
    private String imageUrl; 
    
    @Column(name = "logo_url")
    private String logoUrl; 
    
	 @Column(name = "thump_nail_url")
	private String  thumpNailUrl;
	 
	 @Column(name = "image_path")
		private String imagePath;
		
	@Column(name = "thumb_nail_file_path")
		private String thumbNailFilePath;
	
	
    @Column(name = "party_status")
    private String partyStatus; 
     
    
    @Column(name = "party_established_date")
    private String partyEstablishedDate; 
    
    @Column(name = "party_website_url")
    private String partyWebsiteUrl; 
     
    @Column(name = "additional_info1")
    private String additionalInfo1; 
     
    
    @Column(name = "additional_info2")
    private String additionalInfo2; 
    
    @Column(name = "additional_info3")
    private String additionalInfo3; 
    
    @Column(name = "additional_info4")
    private String additionalInfo4; 
    
    @Column(name = "additional_info5")
    private String additionalInfo5; 
    
    @Column(name = "additional_info6")
    private String additionalInfo6; 
    
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="party_type")
    private PartyType partyType;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="sector_name")
    private Sector sectorName;
    
   /* @OneToMany(mappedBy="party", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    private List<Address> address;*/
    
    @OneToMany(targetEntity = Address.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	 @JoinColumn(name="party_id" , referencedColumnName = "party_id")
	  private List<Address> address;
    
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "party_contact",
        joinColumns = {@JoinColumn(name = "party_id", referencedColumnName = "party_id")},
        inverseJoinColumns = {@JoinColumn(name = "contact_id", referencedColumnName = "contact_id")}) //define foreign key
    @BatchSize(size = 20)
    private Set<Contact> contact = new HashSet<>();
    
    @JsonIgnore 
    @OneToMany(cascade=CascadeType.ALL, mappedBy="party")
    private Set<ItemSupplierParty> itemSupplierParty;
    
	 @OneToMany(targetEntity = BankDetails.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	 @JoinColumn(name="party_id" , referencedColumnName = "party_id")
	 private List<BankDetails> bankDetails;
	 
	 
    public List<BankDetails> getBankDetails() {
		return bankDetails;
	}


	public void setBankDetails(List<BankDetails> bankDetails) {
		this.bankDetails = bankDetails;
	}


	public String getAdditionalInfo6() {
		return additionalInfo6;
	}


	public void setAdditionalInfo6(String additionalInfo6) {
		this.additionalInfo6 = additionalInfo6;
	}


	public String getLogoUrl() {
		return logoUrl;
	}

    
	public Set<Contact> getContact() {
		return contact;
	}


	public void setContact(Set<Contact> contact) {
		this.contact = contact;
	}


	public String getIsOrganisation() {
		return isOrganisation;
	}


	public void setIsOrganisation(String isOrganisation) {
		this.isOrganisation = isOrganisation;
	}


	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
    

public Set<ItemSupplierParty> getItemSupplierParty() {
		return itemSupplierParty;
	}


	public void setItemSupplierParty(Set<ItemSupplierParty> itemSupplierParty) {
		this.itemSupplierParty = itemSupplierParty;
	}


public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
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

	public String getPartyInformation() {
		return partyInformation;
	}

	public void setPartyInformation(String partyInformation) {
		this.partyInformation = partyInformation;
	}

	public Long getEmployeeQuantity() {
		return employeeQuantity;
	}

	public void setEmployeeQuantity(Long employeeQuantity) {
		this.employeeQuantity = employeeQuantity;
	}

	public String getPartyEmailId() {
		return partyEmailId;
	}

	public void setPartyEmailId(String partyEmailId) {
		this.partyEmailId = partyEmailId;
	}

	public String getPartyFaxNumber() {
		return partyFaxNumber;
	}

	public void setPartyFaxNumber(String partyFaxNumber) {
		this.partyFaxNumber = partyFaxNumber;
	}

	public String getPartyMobileNumber() {
		return partyMobileNumber;
	}

	public void setPartyMobileNumber(String partyMobileNumber) {
		this.partyMobileNumber = partyMobileNumber;
	}

	public String getPartyPAN() {
		return partyPAN;
	}

	public void setPartyPAN(String partyPAN) {
		this.partyPAN = partyPAN;
	}

	public String getPartyGSTIN() {
		return partyGSTIN;
	}

	public void setPartyGSTIN(String partyGSTIN) {
		this.partyGSTIN = partyGSTIN;
	}

	public String getPartyUdyogAadhaar() {
		return partyUdyogAadhaar;
	}

	public void setPartyUdyogAadhaar(String partyUdyogAadhaar) {
		this.partyUdyogAadhaar = partyUdyogAadhaar;
	}

	public String getIsSupplier() {
		return isSupplier;
	}

	public void setIsSupplier(String isSupplier) {
		this.isSupplier = isSupplier;
	}

	public String getIsCustomer() {
		return isCustomer;
	}

	public void setIsCustomer(String isCustomer) {
		this.isCustomer = isCustomer;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getPartyStatus() {
		return partyStatus;
	}

	public void setPartyStatus(String partyStatus) {
		this.partyStatus = partyStatus;
	}

	public String getPartyEstablishedDate() {
		return partyEstablishedDate;
	}

	public void setPartyEstablishedDate(String partyEstablishedDate) {
		this.partyEstablishedDate = partyEstablishedDate;
	}

	public String getPartyWebsiteUrl() {
		return partyWebsiteUrl;
	}

	public void setPartyWebsiteUrl(String partyWebsiteUrl) {
		this.partyWebsiteUrl = partyWebsiteUrl;
	}

	public String getAdditionalInfo1() {
		return additionalInfo1;
	}

	public void setAdditionalInfo1(String additionalInfo1) {
		this.additionalInfo1 = additionalInfo1;
	}

	public String getAdditionalInfo2() {
		return additionalInfo2;
	}

	public void setAdditionalInfo2(String additionalInfo2) {
		this.additionalInfo2 = additionalInfo2;
	}

	public String getAdditionalInfo3() {
		return additionalInfo3;
	}

	public void setAdditionalInfo3(String additionalInfo3) {
		this.additionalInfo3 = additionalInfo3;
	}

	public String getAdditionalInfo4() {
		return additionalInfo4;
	}

	public void setAdditionalInfo4(String additionalInfo4) {
		this.additionalInfo4 = additionalInfo4;
	}

	public String getAdditionalInfo5() {
		return additionalInfo5;
	}

	public void setAdditionalInfo5(String additionalInfo5) {
		this.additionalInfo5 = additionalInfo5;
	}

   
    public PartyType getPartyType() {
		return partyType;
	}

	public void setPartyType(PartyType partyType) {
		this.partyType = partyType;
	}

	
	public Sector getSectorName() {
		return sectorName;
	}

	public void setSectorName(Sector sectorName) {
		this.sectorName = sectorName;
	}

	/*public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}*/
	
	public String getThumpNailUrl() {
		return thumpNailUrl;
	}


	public void setThumpNailUrl(String thumpNailUrl) {
		this.thumpNailUrl = thumpNailUrl;
	}


	public String getImagePath() {
		return imagePath;
	}


	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}


	public String getThumbNailFilePath() {
		return thumbNailFilePath;
	}


	public void setThumbNailFilePath(String thumbNailFilePath) {
		this.thumbNailFilePath = thumbNailFilePath;
	}


	@Override
	public String toString() {
		return "Party [partyId=" + partyId + ", partyName=" + partyName + ", partyInformation=" + partyInformation
				+ ", employeeQuantity=" + employeeQuantity + ", partyEmailId=" + partyEmailId + ", partyFaxNumber="
				+ partyFaxNumber + ", partyMobileNumber=" + partyMobileNumber + ", partyPAN=" + partyPAN
				+ ", partyGSTIN=" + partyGSTIN + ", partyUdyogAadhaar=" + partyUdyogAadhaar + ", isSupplier="
				+ isSupplier + ", isCustomer=" + isCustomer + ", imageUrl=" + imageUrl + ", logoUrl=" + logoUrl
				+ ", partyStatus=" + partyStatus + ", partyEstablishedDate=" + partyEstablishedDate
				+ ", partyWebsiteUrl=" + partyWebsiteUrl + ", additionalInfo1=" + additionalInfo1 + ", additionalInfo2="
				+ additionalInfo2 + ", additionalInfo3=" + additionalInfo3 + ", additionalInfo4=" + additionalInfo4
				+ ", additionalInfo5=" + additionalInfo5 + ", partyType=" + partyType + ", sectorName=" + sectorName
				+ ", address=" + "]";
	}


}
