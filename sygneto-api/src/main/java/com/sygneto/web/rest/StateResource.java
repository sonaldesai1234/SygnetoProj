package com.sygneto.web.rest;

import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.State;

import com.sygneto.domain.SygnetoResponse;
import com.sygneto.repository.StateRepository;
import com.sygneto.security.AuthoritiesConstants;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
public class StateResource {

	 private static final String ENTITY_NAME = "state";
    private final Logger log = LoggerFactory.getLogger(StateResource.class);

    @Autowired
    StateRepository stateRepository;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    @Autowired
	CustomResponse customeResponce;
	 
    /**
     * {@code GET  /State-orders} : get all the State.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of State in body.
     */
    @GetMapping("/state")
    //@ApiOperation(value="Get all states")
    @Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
    public Object getAllStates() {
    	
        log.debug("REST request to get all State");
        List<State> result= stateRepository.findAll();
        if(result !=null && !result.isEmpty()) {
        	Object obj=customeResponce.success("Get all Data Successfully", result);
        	return obj;
        }else {
        	Object obj=customeResponce.failure(404, "Data Not found");
        	return obj;
        }
    }
    
    /**
     * {@code GET  /State-/:id} : get the "id" state.
     *
     * @param id the id of the State to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the State, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/state/{id}")
    @Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
    public Object getStateById(@PathVariable Long id) {
        log.debug("REST request to get state : {}", id);
        Optional<State> result = stateRepository.findById(id);
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
