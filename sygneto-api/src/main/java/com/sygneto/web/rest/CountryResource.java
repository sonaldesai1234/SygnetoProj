package com.sygneto.web.rest;

import com.sygneto.domain.Country;
import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.SygnetoResponse;
import com.sygneto.repository.CountryRepository;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
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
 * REST controller for managing {@link com.sygneto.domain.PurchaseOrderOld}.
 */
@RestController
@RequestMapping("/api")
public class CountryResource {

	private static final String ENTITY_NAME = "country";

    private final Logger log = LoggerFactory.getLogger(CountryResource.class);

    @Autowired
    CountryRepository countryRepository;

    @Autowired
	CustomResponse customeResponce;
    
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
 
    /**
     * {@code GET  /purchase-orders} : get all the purchaseOrders.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of purchaseOrders in body.
     */
    @GetMapping("/country")
    public Object getAllPurchaseOrders() {
        log.debug("REST request to get all country");
      
        List<Country> result= countryRepository.findAll();
        if(result !=null&& !result.isEmpty()) {
        	Object obj=customeResponce.success("Get all Data Successfully", result);
        	return obj;
        }else {
        	Object obj=customeResponce.failure(404, "Data Not found");
        	return obj;
        }
       
    }
    
    /**
     * {@code GET  /country-/:id} : get the "id" Country.
     *
     * @param id the id of the Country to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the Country, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/country/{id}")
    public Object getPurchaseOrder(@PathVariable Long id) {
        log.debug("REST request to get country : {}", id);
      
        Optional<Country> result = countryRepository.findById(id);
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
