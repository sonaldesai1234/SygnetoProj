package com.sygneto.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A GstSlabs.
 */
@Entity
@Table(name = "gst_slabs")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)

public class GstSlabs  implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
	@Column(name = "gst_slabs", unique = true, nullable = false)
    private float gstSlabs;

	public float getGstSlabs() {
		return gstSlabs;
	}

	public void setGstSlabs(float gstSlabs) {
		this.gstSlabs = gstSlabs;
	}

	
	

	
    
    
    
    
   
}
