package com.sygneto.web.rest;

import com.sygneto.domain.Address;
import com.sygneto.domain.Contact;
import com.sygneto.domain.ContactType;
import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.SygnetoResponse;
import com.sygneto.service.ContactTypeService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sygneto.domain.ContactType}.
 */
@RestController
@RequestMapping("/api")
public class ContactTypeResource {

    private final Logger log = LoggerFactory.getLogger(ContactTypeResource.class);
    
	
    private static final String ENTITY_NAME = "contactType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContactTypeService contactTypeService;

    public ContactTypeResource(ContactTypeService contactTypeService) {
        this.contactTypeService = contactTypeService;
    }
    @Autowired
	CustomResponse customeResponce;
    /**
     * {@code POST  /contact-types} : Create a new contactType.
     *
     * @param contactType the contactType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contactType, or with status {@code 400 (Bad Request)} if the contactType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
	
    @PostMapping("/contactType")
    @ApiOperation(value="Create Contact Type like Admin,HR etc.")
    public Object createContactType(@RequestBody ContactType contactType) throws URISyntaxException {
    	log.debug("REST request to save ContactType : {}", contactType);
    	
        if (contactType.getContactType()==null) {
        	
        	    throw new BadRequestAlertException("contactType can not be null", ENTITY_NAME, "contactType cant be null");
            }
        ContactType result = contactTypeService.save(contactType);
        Object obj=customeResponce.success("Data Added Successfully", result);
        return obj;
        
        
    }

    /**
     * {@code PUT  /contact-types} : Updates an existing contactType.
     *
     * @param contactType the contactType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactType,
     * or with status {@code 400 (Bad Request)} if the contactType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contactType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
	@ApiOperation(value="Update Contact Type")
    @PutMapping("/contactType")
    public Object updateContactType(@RequestBody ContactType contactType) throws URISyntaxException {
        log.debug("REST request to update ContactType : {}", contactType);
       
        if (contactType.getContactType() == null) {
        	
        	throw new BadRequestAlertException("invalid contactType ", ENTITY_NAME, "contactType cant be null");
            }
       
        ContactType result = contactTypeService.save(contactType);
        Object obj=customeResponce.success("Data Updated Successfully", result);
        return obj;
        
   
    }

    /**
     * {@code GET  /contact-types} : get all the contactType.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contactType in body.
     */
	@ApiOperation(value="Get all Contact Type")
    @GetMapping("/contactType")
    public Object getAllContactTypes() {
        log.debug("REST request to get all ContactTypes");
       
        List<ContactType> result =contactTypeService.findAll();
        if(result !=null&& !result.isEmpty()) {
        	Object obj=customeResponce.success("Get all Data Successfully", result);
        	return obj;
        }else {
        	Object obj=customeResponce.failure(404, "Data Not found");
        	return obj;
        }
    }
    /**
     * {@code GET  /contact-types/:id} : get the "id" contactType.
     *
     * @param id the id of the contactType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contactType, or with status {@code 404 (Not Found)}.
     */
	@ApiOperation(value="Get specific Contact Type as per Contact Type specified here ")
    @GetMapping("/contactType/{contactType}")
    public Object getContactType(@PathVariable String contactType) {
        log.debug("REST request to get ContactType : {}", contactType);
       
        Optional<ContactType> result = contactTypeService.findOneByContactType(contactType);
        if(result !=null && result.isPresent()) {
        	Object obj=customeResponce.success("Get all Data Successfully", result);
        	return obj;
        }else 
        {
        	Object obj=customeResponce.failure(404, "Data Not found");
        	return obj;
        }
        
    }

    /**
     * {@code DELETE  /contact-types/:id} : delete the "id" contactType.
     *
     * @param id the id of the contactType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
	
	@ApiOperation(value="Delete Specific Contact Type")
    @DeleteMapping("/contactType/{contactType}")
    public Object deleteContactType(@PathVariable String contactType) {
   
        Optional<ContactType> result = contactTypeService.findOne(contactType);
        if(result !=null  && result.isPresent())
        {
       	 if(contactType !=null) 
       	 {
       		 contactTypeService.delete(contactType);
       	     Object obj=customeResponce.success("Get Deleted Successfully", null);
           	 return obj;
             }
       	 else{
           	    Object obj=customeResponce.failure(404, "Data Not found");
             	return obj;
             }
       }
      else 
      {
   	    Object obj=customeResponce.failure(404, "Data Not found");
      	return obj;
      }
}
}
