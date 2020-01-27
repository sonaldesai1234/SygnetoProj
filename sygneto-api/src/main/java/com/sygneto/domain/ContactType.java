package com.sygneto.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ContactType.
 */
@Entity
@Table(name = "contact_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContactType extends AbstractAuditingEntity implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "contact_type",unique = true, nullable = false)
    private String contactType;
	
    public String getContactType() {
		return contactType;
	}
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}
    
    

}
