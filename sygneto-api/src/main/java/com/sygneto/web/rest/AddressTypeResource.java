package com.sygneto.web.rest;

import com.sygneto.domain.AddressType;
import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.State;
import com.sygneto.repository.AddressTypeRepository;
import com.sygneto.service.AddressTypeService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
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
 * REST controller for managing {@link com.sygneto.domain.AddressType}.
 */
@RestController
@RequestMapping("/api")
public class AddressTypeResource {

    private final Logger log = LoggerFactory.getLogger(AddressTypeResource.class);

    private static final String ENTITY_NAME = "addressType";
    
    @Autowired
    AddressTypeRepository addressTypeRepository;

    @Autowired
   	CustomResponse customeResponce;
   	 
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AddressTypeService addressTypeService;

    public AddressTypeResource(AddressTypeService addressTypeService) {
        this.addressTypeService = addressTypeService;
    }

  
    /**
     * {@code GET  /address-types} : get all the addressTypes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of addressTypes in body.
     */
    @GetMapping("/addressTypes")
	@ApiOperation(value="Get All different types of address like Shipping , Billing etc.")

    public Object getAllAddressTypes() {
        log.debug("REST request to get all AddressTypes");
        List<AddressType> result= addressTypeRepository.findAll();
        if(result !=null && !result.isEmpty()) {
        	
        	return customeResponce.success("Get all Data Successfully", result);
        }else {
        	
        	return customeResponce.failure(404, "Data Not found");
        }

    }

    /**
     * {@code GET  /address-types/:id} : get the "id" addressType.
     *
     * @param id the id of the addressType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the addressType, or with status {@code 404 (Not Found)}.
     */
   /* @GetMapping("/address-types/{id}")
    public Object getAddressType(@PathVariable Long id) {
        log.debug("REST request to get AddressType : {}", id);
        Optional<AddressType> result = addressTypeService.findOne(id);
        if(result !=null && result.isPresent()) {
        	
        	return customeResponce.success("Get all Data Successfully", result);
        }else 
        {
        	
        	return customeResponce.failure(404, "Data Not found");
        }
    }
*/
    
}
