package com.sygneto.domain;

import java.io.Serializable;
import java.time.Instant;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Employee.
 */
@Entity
@Table(name = "employees")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Employees extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id", unique = true, nullable = false)
	private Long employeeId;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "login_allowed")
	private boolean loginAllowed;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "employee_number")
	private String employeeNumber;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "gender")
	private String gender;

	@Column(name = "date_of_birth")
	private Instant dateOfBirth;
	
	@Column(name = "date_of_joining")
	private Instant dateOfJoining;

	@Column(name = "date_of_release")
	private Instant dateOfRelease;

	@Column(name = "blood_group")
	private String bloodGroup;

	@Column(name = "emergency_contact_number")
	private String emergencyContactNumber;

	@Column(name = "aadhar_number")
	private String aadharNumber;

	@Column(name = "pan_number")
	private String panNumber;

	@Column(name = "driving_licence_number")
	private String drivingLicenseNumber;

	@Column(name = "driving_licence_valid_till")
	private Instant drivingLicenseValidTill;

	@Column(name = "passport_number")
	private String passportNumber;

	@Column(name = "passport_validity_till")
	private Instant passportValidityTill;

	@Column(name = "status")
	private String status;

	@Column(name = "designation")
	private String designation;

	@Column(name = "department")
	private String department;

	@Column(name = "reports_to")
	private Long reportTo;

	@Column(name = "image_url")
	private String imageUrl;
	
	 @Column(name = "thumb_nail_url")
	private String  thumbNailUrl;
	 
	 @Column(name = "image_path")
		private String imagePath;
		
	@Column(name = "thumb_nail_file_path")
		private String thumbNailFilePath;

	@Column(name = "authorities")
	private String authorities;

	@OneToMany(mappedBy="employees", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    private List<Address> address;

	/*@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "employee_address",
        joinColumns = {@JoinColumn(name = "employee_id", referencedColumnName = "employee_id")},
        inverseJoinColumns = {@JoinColumn(name = "address_id", referencedColumnName = "address_id")}) //define foreign key
    //@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Address> address = new HashSet<>();*/
	
/*	public Set<Address> getAddress() {
		return address;
	}

	public void setAddress(Set<Address> address) {
		this.address = address;
	}*/


	public void setAuthorities(String authorities) {
		this.authorities = authorities;
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






	public String getThumbNailUrl() {
		return thumbNailUrl;
	}




	public void setThumbNailUrl(String thumbNailUrl) {
		this.thumbNailUrl = thumbNailUrl;
	}




	public boolean isLoginAllowed() {
		return loginAllowed;
	}




	public String getAuthorities() {
		return authorities;
	}







	public List<Address> getAddress() {
		return address;
	}




	public void setAddress(List<Address> address) {
		this.address = address;
	}




	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setLoginAllowed(boolean loginAllowed) {
		this.loginAllowed = loginAllowed;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public Employees employeeId(Long employeeId) {
		this.employeeId = employeeId;
		return this;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here, do not remove

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Instant getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Instant dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	public Instant getDateOfJoining() {
		return dateOfJoining;
	}




	public void setDateOfJoining(Instant dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}




	public Instant getDateOfRelease() {
		return dateOfRelease;
	}

	public void setDateOfRelease(Instant dateOfRelease) {
		this.dateOfRelease = dateOfRelease;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getEmergencyContactNumber() {
		return emergencyContactNumber;
	}

	public void setEmergencyContactNumber(String emergencyContactNumber) {
		this.emergencyContactNumber = emergencyContactNumber;
	}

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getDrivingLicenseNumber() {
		return drivingLicenseNumber;
	}

	public void setDrivingLicenseNumber(String drivingLicenseNumber) {
		this.drivingLicenseNumber = drivingLicenseNumber;
	}

	public Instant getDrivingLicenseValidTill() {
		return drivingLicenseValidTill;
	}

	public void setDrivingLicenseValidTill(Instant drivingLicenseValidTill) {
		this.drivingLicenseValidTill = drivingLicenseValidTill;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public Instant getPassportValidityTill() {
		return passportValidityTill;
	}

	public void setPassportValidityTill(Instant passportValidityTill) {
		this.passportValidityTill = passportValidityTill;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Long getReportTo() {
		return reportTo;
	}

	public void setReportTo(Long reportTo) {
		this.reportTo = reportTo;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Employees)) {
			return false;
		}
		return employeeId != null && employeeId.equals(((Employees) o).employeeId);
	}

	@Override
	public int hashCode() {
		return 31;
	}


}
