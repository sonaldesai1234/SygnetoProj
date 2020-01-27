package com.sygneto.web.rest;



import com.sygneto.domain.CustomResponse;

import com.sygneto.domain.ProductMRPHistory;
import com.sygneto.domain.ProductMrp;

import com.sygneto.repository.ProductMRPHistoryRepository;
import com.sygneto.repository.ProductRepository;
import com.sygneto.service.ProductMRPHistoryService;
import com.sygneto.service.ProductMrpService;

import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sygneto.domain.ProductMrp}.
 */
@RestController
@RequestMapping("/api")
public class ProductMrpResource {

    private final Logger log = LoggerFactory.getLogger(ProductMrpResource.class);

    private static final String ENTITY_NAME = "productMrp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductMrpService productMrpService;

    @Autowired
	CustomResponse customResponse;
    
    @Autowired
    ProductMRPHistoryRepository productMRPHistoryRepository;
    
    @Autowired
    ProductRepository productRepository;
    
    
    @Autowired
     ProductMRPHistoryService   productMRPHistoryService;
    public ProductMrpResource(ProductMrpService productMrpService) {
        this.productMrpService = productMrpService;
    }

    /**
     * {@code POST  /product-mrps} : Create a new productMrp.
     *
     * @param productMrp the productMrp to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productMrp, or with status {@code 400 (Bad Request)} if the productMrp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @ApiOperation(value="Assign price for product")
    @PostMapping("/productMrp")
    public Object createProductMrp(@RequestBody ProductMrp productMrp) throws URISyntaxException {
        log.debug("REST request to save ProductMrp : {}", productMrp);
        if (productMrp.getProductMrpId() != null) {
            throw new BadRequestAlertException("A new productMrp cannot already have an ID", ENTITY_NAME, "idexists");
        }
       /* if(!productMrp.getValidTill().isBefore(productMrp.getValidFrom()))
        {
        	
        	customResponse.failure(400, "Date mismatch");
        
        }*/
       
        ProductMrp result = productMrpService.save(productMrp);

        return customResponse.success("product mrp created", result);

        
      
    }

    /**
     * {@code PUT  /product-mrps} : Updates an existing productMrp.
     *
     * @param productMrp the productMrp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productMrp,
     * or with status {@code 400 (Bad Request)} if the productMrp is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productMrp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
   /* @PutMapping("/productMrp")
    public Object updateProductMrp(@RequestBody ProductMrp productMrp) throws URISyntaxException {
        log.debug("REST request to update ProductMrp : {}", productMrp);
        if (productMrp.getProductMrpId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if(productMrp.getValidFrom()!=null&&productMrp.getValidTill()!=null)
        {
        	Instant date1=productMrp.getValidFrom();
        	System.out.println("******************"+date1);
      
        long id=productMrp.getProductMrpId();
        float price= productMrp.getProductMrpPrice();
        
        
        
        
        
        ProductMrp result = productMrpService.save(productMrp);
		return customResponse.success("product mrp updated", result);
    }*/
    @ApiOperation(value="Update price of existing product based on product id")
    @PutMapping("/productMrp")
	public Object updateProductMrp(@RequestBody ProductMrp productMrp) throws URISyntaxException {
		log.debug("REST request to save PurchaseOrder : {}", productMrp);
		if(productMrp.getProductMrpId()==null)
		{
			//throw error
		}		

		  Optional<ProductMrp> result = productMrpService.findById(productMrp.getProductMrpId());
		  ProductMRPHistory productMRPHistory=new ProductMRPHistory();
		  productMRPHistory.setProductMrpPrice(result.get().getProductMrpPrice());
		  productMRPHistory.setTotalProductPrice(result.get().getTotalProductPrice());
		  productMRPHistory.setValidFrom(result.get().getValidFrom());
		  productMRPHistory.setValidTill(result.get().getValidTill());
		  ProductMRPHistory result1 = productMRPHistoryRepository.save(productMRPHistory);
		  
		  ProductMrp result2 = productMrpService.save(productMrp);
	     
		  result1.setValidTill(result2.getValidFrom());
	      productMRPHistoryRepository.save(result1);
	    
		return customResponse.success("product mrp updated", result2);
    }

    /**
     * {@code GET  /product-mrps} : get all the productMrps.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productMrps in body.
     */
    @ApiOperation(value="Get price all products")
   @GetMapping("/productMrp")
    public Object getAllProductMrps() {
        log.debug("REST request to get all ProductMrps");
        
       /* ProductMrp productMrp=new ProductMrp();
        Product product=new Product();
        float price=productMrp.getProductMrpPrice();
        float sac=product.getSac();
        float totalPrice=(price*sac);
        productMrp.setTotalProductPrice(totalPrice);*/
    
       
        List<ProductMrp> result= productMrpService.findAll();
      
        if(result !=null && !result.isEmpty()) {
        	
        	
        	Object obj=customResponse.success("product mrp found", result);
        	return obj;
        }else {
        	Object obj=customResponse.failure(404, "product mrp Not found");
        	return obj;
        }

    }
/*    @GetMapping("/productMrp")
    public Object getAllProductMrps(@Param("validFrom")@ModelAttribute Instant validFrom,@Param("validTill")@ModelAttribute Instant validTill)
    {
        log.debug("REST request to get all ProductMrps");
         
           List<ProductMrp>result=productMrpService.findAllBetweenDates(validFrom,validTill);
        
        if(result !=null && !result.isEmpty()) {
        	
        	
        	Object obj=customResponse.success("product mrp found", result);
        	return obj;
        	
        
        }
        else
        {
        	Object obj=customResponse.failure(404, "product mrp Not found");
        	return obj;
        }

    }
*/

    /**
     * {@code GET  /product-mrps/:id} : get the "id" productMrp.
     *
     * @param id the id of the productMrp to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productMrp, or with status {@code 404 (Not Found)}.
     */
    @ApiOperation(value="get price of specific product as per product id")
    @GetMapping("/productMrp/{id}")
    public Object getProductMrp(@PathVariable Long id) {
        log.debug("REST request to get ProductMrp : {}", id);
       Optional<ProductMrp> result = productMrpService.findById(id);
      
        if(result !=null && result.isPresent()) {
        	Object obj=customResponse.success("product mrp found", result);
        	return obj;
        }else 
        {
        	Object obj=customResponse.failure(404, "product mrp Not found");
        	return obj;
        }
    }
    
    /*@GetMapping("/productMrpProductId/{productId}")
    public Object getProductMrpbyProduct(@PathVariable Long productId) {
        log.debug("REST request to get ProductMrp : {}", productId);
        Optional<ProductMrp> result = productMrpService.findByProductProductId(productId);
        if(result !=null && result.isPresent()) {
        	Object obj=customResponse.success("product mrp found", result);
        	return obj;
        }else 
        {
        	Object obj=customResponse.failure(404, "product mrp Not found");
        	return obj;
        }
    }*/
   

    /**
     * {@code DELETE  /product-mrps/:id} : delete the "id" productMrp.
     *
     * @param id the id of the productMrp to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @ApiOperation(value="Delete price of specific product as per product id")
    @DeleteMapping("/productMrp/{id}")
    public Object deleteProductMrp(@PathVariable Long id) {
		Optional<ProductMrp> result = productMrpService.findOne(id);
		
        if (result != null && result.isPresent()) {
			if (id != null) {
		        productMrpService.delete(id);
				return customResponse.success("product mrp deleted", null);

			} else {
				return customResponse.failure(404, "product mrp  not found");

			}
		} else {
			return customResponse.failure(404, "product mrp not deleted");

		}    
        }
}
