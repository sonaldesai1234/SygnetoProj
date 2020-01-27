package com.sygneto.web.rest;

import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.SalesOrder;
import com.sygneto.domain.SalesOrderProduct;
import com.sygneto.security.AuthoritiesConstants;
import com.sygneto.service.SalesOrderProductService;
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
 * REST controller for managing {@link com.sygneto.domain.SalesOrderProduct}.
 */
@RestController
@RequestMapping("/api")
public class SalesOrderProductResource {

    private final Logger log = LoggerFactory.getLogger(SalesOrderProductResource.class);

    private static final String ENTITY_NAME = "salesOrderProduct";


    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
	CustomResponse customResponse;
    private final SalesOrderProductService salesOrderProductService;

    public SalesOrderProductResource(SalesOrderProductService salesOrderProductService) {
        this.salesOrderProductService = salesOrderProductService;
    }

    /**
     * {@code POST  /sales-order-products} : Create a new salesOrderProduct.
     *
     * @param salesOrderProduct the salesOrderProduct to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salesOrderProduct, or with status {@code 400 (Bad Request)} if the salesOrderProduct has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/salesOrderProducts")
    @Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
    public Object createSalesOrderProduct(@RequestBody SalesOrderProduct salesOrderProduct) throws URISyntaxException {
        log.debug("REST request to save SalesOrderProduct : {}", salesOrderProduct);
        if (salesOrderProduct.getSalesOrderProductId() != null) {
            throw new BadRequestAlertException("A new salesOrderProduct cannot already have an ID", ENTITY_NAME, "idexists");
        }
     
        
        SalesOrderProduct result = salesOrderProductService.save(salesOrderProduct);
        return customResponse.success("Sales order product created", result);

    }

    /**
     * {@code PUT  /sales-order-products} : Updates an existing salesOrderProduct.
     *
     * @param salesOrderProduct the salesOrderProduct to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesOrderProduct,
     * or with status {@code 400 (Bad Request)} if the salesOrderProduct is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salesOrderProduct couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/salesOrderProducts")
    @Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
    public Object updateSalesOrderProduct(@RequestBody SalesOrderProduct salesOrderProduct) throws URISyntaxException {
        log.debug("REST request to update SalesOrderProduct : {}", salesOrderProduct);
        if (salesOrderProduct.getSalesOrderProductId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SalesOrderProduct result = salesOrderProductService.save(salesOrderProduct);
        return customResponse.success("Sales order product updated", result);

    }

    /**
     * {@code GET  /sales-order-products} : get all the salesOrderProducts.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salesOrderProducts in body.
     */
    @GetMapping("/salesOrderProducts")
    @Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
    public Object getAllSalesOrderProducts() {
    	 List<SalesOrderProduct> result=salesOrderProductService.findAll();
         
         if(result !=null&& !result.isEmpty()) {
         	return customResponse.success("Sales order product found", result);

         	
         }else 
         {
         	return customResponse.failure(404, "Sales order product  found");
         	
         }
    }

    /**
     * {@code GET  /sales-order-products/:id} : get the "id" salesOrderProduct.
     *
     * @param id the id of the salesOrderProduct to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salesOrderProduct, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/salesOrderProducts/{id}")
    @Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
    public Object getSalesOrderProduct(@PathVariable Long id) {
        log.debug("REST request to get SalesOrderProduct : {}", id);
        Optional<SalesOrderProduct> result = salesOrderProductService.findOne(id);        
        if(result !=null && result.isPresent()) {
        	return customResponse.success("Sales order product found", result);
        	
        }else 
        {
        	return customResponse.failure(404, "Sales order product not found");
        	
        }    }

    /**
     * {@code DELETE  /sales-order-products/:id} : delete the "id" salesOrderProduct.
     *
     * @param id the id of the salesOrderProduct to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/salesOrderProducts/{id}")
    @Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
    public Object deleteSalesOrderProduct(@PathVariable Long id) {
        log.debug("REST request to delete SalesOrderProduct : {}", id);
        Optional<SalesOrderProduct> result = salesOrderProductService.findOne(id);
        if(result.isPresent()) {
        	
        	
        	try {
                salesOrderProductService.delete(id);
				return customResponse.success("Sales order product deleted", null);
			} catch (Exception e) {
				return customResponse.failure(400, "Sales order product not found");
			}
        	
        	
        	 
       }else {
    	  return customResponse.failure(404, "Sales order product not deleted");
       	
       }    }
}
