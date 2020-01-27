package com.sygneto.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A AddressType.
 */
@Entity
@Table(name = "address_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AddressType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "address_type",nullable = false)
    private String addressType;
    
    public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	
   
}
