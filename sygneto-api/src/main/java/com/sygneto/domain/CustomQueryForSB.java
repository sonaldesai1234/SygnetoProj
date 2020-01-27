package com.sygneto.domain;
import org.hibernate.annotations.Cache;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CacheConcurrencyStrategy;


import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

/**
 * A SalesOrderProduct.
 */
public class CustomQueryForSB implements Serializable {

    private Long partyId;
    
    private Long month;
    
    private Long poCount;

	public Long getPartyId() {
		return partyId;
	}

	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}

	public Long getMonth() {
		return month;
	}

	public void setMonth(Long month) {
		this.month = month;
	}

	public Long getPoCount() {
		return poCount;
	}

	public void setPoCount(Long poCount) {
		this.poCount = poCount;
	}

	@Override
	public String toString() {
		return "CustomQueryForSB [partyId=" + partyId + ", month=" + month + ", poCount=" + poCount + "]";
	}

	public CustomQueryForSB(Long partyId, Long month, Long poCount) {
		super();
		this.partyId = partyId;
		this.month = month;
		this.poCount = poCount;
	}
	
	
    
}