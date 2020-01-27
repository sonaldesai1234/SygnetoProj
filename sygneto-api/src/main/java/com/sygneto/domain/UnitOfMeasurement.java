package com.sygneto.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ContactType.
 */
@Entity
@Table(name = "unit_of_measurement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UnitOfMeasurement  implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uom_id",unique = true, nullable = false)
    private Long UOMId;
	
    @Column(name = "UOM",unique = true, nullable = false)
    private String UOM;

	public Long getUOMId() {
		return UOMId;
	}

	public void setUOMId(Long uOMId) {
		UOMId = uOMId;
	}

	public String getUOM() {
		return UOM;
	}

	public void setUOM(String uOM) {
		UOM = uOM;
	}

	@Override
	public String toString() {
		return "UnitOfMeasurement [UOMId=" + UOMId + ", UOM=" + UOM + "]";
	}

	
}
