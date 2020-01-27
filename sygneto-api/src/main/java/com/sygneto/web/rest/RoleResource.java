package com.sygneto.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sygneto.domain.Authority;
import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.Role;
import com.sygneto.domain.Sector;
import com.sygneto.domain.SygnetoResponse;
import com.sygneto.repository.AuthorityRepository;
import com.sygneto.repository.RoleRepository;
import com.sygneto.security.AuthoritiesConstants;
import com.sygneto.service.RoleService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;

/**
 * REST controller for managing Role.
 */
@RestController
@RequestMapping("/api")
public class RoleResource {

	private final Logger log = LoggerFactory.getLogger(RoleResource.class);

	private static final String ENTITY_NAME = "role";

	
	private final RoleService roleService;

	@Autowired
	CustomResponse customeResponce;
	 
	 @Autowired
	 AuthorityRepository authorityRepository;
	 
	@Autowired
	RoleRepository roleRepository;
	@Value("${jhipster.clientApp.name}")
    private String applicationName;
	
	public RoleResource(RoleService roleService) {
		this.roleService = roleService;
	}

	/**
	 * POST /roles : Create a new role.
	 *
	 * @param role
	 *            the role to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         role, or with status 400 (Bad Request) if the role has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */

	@PostMapping("/role")
	//@PreAuthorize("@lmsAuth.hasPermision('','CREATE','ROLE')")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object createRole(@RequestBody Role role) throws URISyntaxException {
		log.debug("REST request to save Role : {}", role);
			Optional<Authority> auth=authorityRepository.findById(role.getRoleName().toUpperCase());
			if(auth !=null || !auth.isPresent()) {
				Authority authority=new Authority();
				authority.setName("ROLE_".concat(role.getRoleName().toUpperCase()));
				authorityRepository.save(authority);
			}
		
			Role result = roleService.save(role);
			Object obj=customeResponce.success("Data Added Successfully", result);
			return obj;

	}

	@GetMapping("/role")
	//@PreAuthorize("@lmsAuth.hasPermision('','VIEW','ROLE')")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object getAllRoles() {
		 SygnetoResponse res=new SygnetoResponse();
		log.debug("REST request to get all Roles");
		List<Role> result = roleService.findAll();
		 if(result !=null && !result.isEmpty()) {
	        	Object obj=customeResponce.success("Get all Data Successfully", result);
	        	return obj;
	        }else {
	        	Object obj=customeResponce.failure(404, "Data Not found");
	        	return obj;
	        }
	}
	
	/*@DeleteMapping("/role/{roleName}")
    public Object deleteSector(@PathVariable String rolename) {
        log.debug("REST request to delete Sector : {}", rolename);
        Optional<Role> result = roleRepository.findById(rolename);
        if(result !=null  && result.isPresent()) {
        	 if(rolename !=null) {
        		 roleRepository.deleteById(rolename);
        	    Object obj=customeResponce.success("Get Deleted Successfully", null);
            	return obj;
              }else {
            	  Object obj=customeResponce.failure(404, "Data Not found");
              	return obj;
              }
       }else {
    	   Object obj=customeResponce.failure(404, "Data Not found");
       	return obj;
       }
    }*/
}
	