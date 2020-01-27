package com.sygneto.web.rest;

import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.UnitOfMeasurement;

import com.sygneto.domain.SygnetoResponse;
import com.sygneto.repository.UnitOfMeasurementRepository;
import com.sygneto.security.AuthoritiesConstants;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sygneto.domain.State}.
 */
@RestController
@RequestMapping("/api")
public class UnitOfMeasurementResource {

	 private static final String ENTITY_NAME = "UnitOfMeasurement";
    private final Logger log = LoggerFactory.getLogger(UnitOfMeasurementResource.class);

    @Autowired
    UnitOfMeasurementRepository unitOfMeasurementRepository;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
	CustomResponse customeResponce;
	 
    /**
     * {@code GET  /uom-orders} : get all the State.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uom in body.
     */
    @GetMapping("/uom")
    @Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
    public Object getAllUOM() {
        log.debug("REST request to get all uom");
        List<UnitOfMeasurement> result= unitOfMeasurementRepository.findAll();
        if(result !=null) {
        	Object obj=customeResponce.success("Get all Data Successfully", result);
        	return obj;
        }else {
        	Object obj=customeResponce.failure(404, "Data Not found");
        	return obj;
        }
    }
    
    /**
     * {@code GET  /uom-/:id} : get the "id" state.
     *
     * @param id the id of the uom to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uom, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/uom/{id}")
    @Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
    public Object getUomById(@PathVariable Long id) {
        log.debug("REST request to get uom : {}", id);
        Optional<UnitOfMeasurement> result = unitOfMeasurementRepository.findById(id);
        if(result !=null && result.isPresent()) {
        	Object obj=customeResponce.success("Get all Data Successfully", result);
        	return obj;
        }else 
        {
        	Object obj=customeResponce.failure(404, "Data Not found");
        	return obj;
        }
        
    }
}
