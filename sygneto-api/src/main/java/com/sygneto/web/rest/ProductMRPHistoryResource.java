package com.sygneto.web.rest;

import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.Product;
import com.sygneto.domain.ProductMRPHistory;
import com.sygneto.service.ProductMRPHistoryService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

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
 * REST controller for managing {@link com.sygneto.domain.ProductMRPHistory}.
 */
@RestController
@RequestMapping("/api")
public class ProductMRPHistoryResource {

    private final Logger log = LoggerFactory.getLogger(ProductMRPHistoryResource.class);

    private static final String ENTITY_NAME = "productMRPHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    CustomResponse customResponse; 
    private final ProductMRPHistoryService productMRPHistoryService;

    public ProductMRPHistoryResource(ProductMRPHistoryService productMRPHistoryService) {
        this.productMRPHistoryService = productMRPHistoryService;
    }

    /**
     * {@code POST  /product-mrp-histories} : Create a new productMRPHistory.
     *
     * @param productMRPHistory the productMRPHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productMRPHistory, or with status {@code 400 (Bad Request)} if the productMRPHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @ApiIgnore
    @PostMapping("/productMrpHistories")
    public ResponseEntity<ProductMRPHistory> createProductMRPHistory(@RequestBody ProductMRPHistory productMRPHistory) throws URISyntaxException {
        log.debug("REST request to save ProductMRPHistory : {}", productMRPHistory);
        if (productMRPHistory.getProductMrpHistoryId() != null) {
            throw new BadRequestAlertException("A new productMRPHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
       
        Product prod=new Product();
        ProductMRPHistory productMRPHistoryObj=new ProductMRPHistory();
        
        //productMRPHistoryObj.setProduct(prod.getProductId());
        
        
        ProductMRPHistory result = productMRPHistoryService.save(productMRPHistory);
        return ResponseEntity.created(new URI("/api/product-mrp-histories/" + result.getProductMrpHistoryId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getProductMrpHistoryId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-mrp-histories} : Updates an existing productMRPHistory.
     *
     * @param productMRPHistory the productMRPHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productMRPHistory,
     * or with status {@code 400 (Bad Request)} if the productMRPHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productMRPHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @ApiIgnore
    @PutMapping("/productMrpHistories")
    public ResponseEntity<ProductMRPHistory> updateProductMRPHistory(@RequestBody ProductMRPHistory productMRPHistory) throws URISyntaxException {
        log.debug("REST request to update ProductMRPHistory : {}", productMRPHistory);
        if (productMRPHistory.getProductMrpHistoryId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductMRPHistory result = productMRPHistoryService.save(productMRPHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productMRPHistory.getProductMrpHistoryId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-mrp-histories} : get all the productMRPHistories.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productMRPHistories in body.
     */
    @ApiOperation(value="Get product price history")
    @GetMapping("/productMrpHistories")
    public List<ProductMRPHistory> getAllProductMRPHistories() {
        log.debug("REST request to get all ProductMRPHistories");
        List<ProductMRPHistory> result=productMRPHistoryService.findAll();
        for (ProductMRPHistory productMRPHistory : result) {
			if(productMRPHistory.getValidFrom() ==null && productMRPHistory.getValidTill() ==null) {
				result.remove(productMRPHistory);
			}
		}
		return result;
    }

    /**
     * {@code GET  /product-mrp-histories/:id} : get the "id" productMRPHistory.
     *
     * @param id the id of the productMRPHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productMRPHistory, or with status {@code 404 (Not Found)}.
     */
    /*@GetMapping("/productMrpHistory/{id}")
    public Object getProductMRPHistory(@PathVariable Long id) {
        log.debug("REST request to get ProductMRPHistory : {}", id);
        Optional<ProductMRPHistory> result = productMRPHistoryService.findOne(id);
        if(result !=null && result.isPresent()) 
        {
        
        	return customResponse.success("product mrp found", result);
        }else 
        {
        	
        	return customResponse.failure(404, "product mrp Not found");
        }
    }*/
    
    @ApiOperation(value="Get product price history as per product Id")
    @GetMapping("/productMrpHistories/{productId}")
    public Object getProductMRPHistoryy(@PathVariable Long productId) {
        log.debug("REST request to get ProductMRPHistory : {}", productId);
        
        List<ProductMRPHistory> result = productMRPHistoryService.findAllByProductId(productId);
       
        if(result !=null && !result.isEmpty())
        {
        
        	return customResponse.success("product History found", result);
        }else 
        {
        	
        	return customResponse.failure(404, "product History Not found");
        }
    }
   

    /**
     * {@code DELETE  /product-mrp-histories/:id} : delete the "id" productMRPHistory.
     *
     * @param id the id of the productMRPHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @ApiOperation(value="Delete history of product price as per product id")
    @DeleteMapping("/productMrpHistories/{id}")
    public ResponseEntity<Void> deleteProductMRPHistory(@PathVariable Long id) {
        log.debug("REST request to delete ProductMRPHistory : {}", id);
        productMRPHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
