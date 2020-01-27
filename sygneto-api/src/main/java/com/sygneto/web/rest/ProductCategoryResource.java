package com.sygneto.web.rest;

import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.ItemCategory;
import com.sygneto.domain.ProductCategory;

import com.sygneto.domain.SygnetoResponse;
import com.sygneto.repository.ProductCategoryRepository;
import com.sygneto.repository.StateRepository;
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
 * REST controller for managing {@link com.sygneto.domain.ProductCategory}.
 */
@RestController
@RequestMapping("/api")
public class ProductCategoryResource {

	 private static final String ENTITY_NAME = "ProductCategory";
    private final Logger log = LoggerFactory.getLogger(ProductCategoryResource.class);

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    @Autowired
	CustomResponse customeResponce;
	 
    @ApiOperation(value="Create new product category")
    @PostMapping("/productCategory")
	public Object createItemCategory(@RequestBody ProductCategory productCategory) throws URISyntaxException {
		log.debug("REST request to save ItemCategory : {}", productCategory);
		if (productCategory.getProductCategoryId() != null) {
			throw new BadRequestAlertException("A new productCategory cannot already have an ID", ENTITY_NAME, "idexists");
		}
		ProductCategory result = productCategoryRepository.save(productCategory);

		return customeResponce.success("Item category created ", result);

	}
    @ApiOperation(value="Update exixting  product category")
    @PutMapping("/productCategory")
	public Object updateItemCategory(@RequestBody ProductCategory productCategory) throws URISyntaxException {
		log.debug("REST request to update productCategory : {}", productCategory);
		if (productCategory.getProductCategoryId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		ProductCategory result = productCategoryRepository.save(productCategory);

		return customeResponce.success("Item category updated ", result);

	}
    /**
     * {@code GET  /State-orders} : get all the productCategory.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of State in body.
     */
    @ApiOperation(value="Get all product category")

    @GetMapping("/productCategory")
    public Object getAllStates() {
        log.debug("REST request to get all productCategory");
        List<ProductCategory> result= productCategoryRepository.findAll();
        if(result !=null && !result.isEmpty()) {
        	Object obj=customeResponce.success("Get all Data Successfully", result);
        	return obj;
        }else {
        	Object obj=customeResponce.failure(404, "Data Not found");
        	return obj;
        }
    }
    
    
    
    /**
     * {@code GET  /State-/:id} : get the "id" productCategoryRepository.
     *
     * @param id the id of the State to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the State, or with status {@code 404 (Not Found)}.
     */
    @ApiOperation(value="Get specific product category based on product category Id")

    @GetMapping("/productCategory/{id}")
    public Object getStateById(@PathVariable Long id) {
        log.debug("REST request to get productCategoryRepository : {}", id);
        Optional<ProductCategory> result = productCategoryRepository.findById(id);
        if(result !=null && result.isPresent()) {
        	Object obj=customeResponce.success("Get all Data Successfully", result);
        	return obj;
        }else 
        {
        	Object obj=customeResponce.failure(404, "Data Not found");
        	return obj;
        }
        
    }
    @ApiOperation(value="Delete specific product category by product category id")
    @DeleteMapping("/productCategory/{id}")
	public Object deleteItemCategory(@PathVariable Long id) {
		log.debug("REST request to delete productCategory : {}", id);
		 Optional<ProductCategory> result = productCategoryRepository.findById(id);
		if (result != null && result.isPresent()) {

			try {
				productCategoryRepository.deleteById(id);
				return customeResponce.success("productCategory  deleted ", null);
			} catch (Exception e) {
				return customeResponce.failure(400, "IproductCategory not deleted ");
			}

		} else {
			return customeResponce.failure(404, "productCategory not found ");

		}
	}
    
    
}
