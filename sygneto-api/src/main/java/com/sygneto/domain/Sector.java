package com.sygneto.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Sector.
 */
@Entity
@Table(name = "sector")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sector extends AbstractAuditingEntity implements Serializable {

   // private static final long serialVersionUID = 1L;

 	private static final long serialVersionUID = 1L;
	@Id
    @Column(name = "sector_name", unique = true, nullable = false)
    private String sectorName;
    
 
    public String getSectorName() {
		return sectorName;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

    @Override
    public int hashCode() {
        return 31;
    }

  
}
