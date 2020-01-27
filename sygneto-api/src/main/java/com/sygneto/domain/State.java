package com.sygneto.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A state.
 */
@Entity
@Table(name = "state")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class State  implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "state_code")
    private Long stateCode;
	
    @Column(name = "state_name",unique = true,nullable = false)
    private String stateName;

	public Long getStateCode() {
		return stateCode;
	}

	public void setStateCode(Long stateCode) {
		this.stateCode = stateCode;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	@Override
	public String toString() {
		return "State [stateCode=" + stateCode + ", stateName=" + stateName + "]";
	}

}
