package com.sygneto.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Role.
 */
@Entity
@Table(name = "role")
public class Role extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "role_name")
    private String  roleName;

    @Column(name = "status")
    private String  status;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Role [roleName=" + roleName + ", status=" + status + "]";
	}

    
   
	

    
}
