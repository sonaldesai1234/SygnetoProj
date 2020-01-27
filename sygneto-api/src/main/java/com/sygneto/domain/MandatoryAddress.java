package com.sygneto.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

/**
 * A MandatoryAddress.
 */
@Entity
@Table(name = "mandatory_address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MandatoryAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="mandatory_address_id")
    private Long mandatoryAddressId;
    
    private Party party;
    
    //private List<Address> address;

	public Long getMandatoryAddressId() {
		return mandatoryAddressId;
	}

	public void setMandatoryAddressId(Long mandatoryAddressId) {
		this.mandatoryAddressId = mandatoryAddressId;
	}

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	
    

}
