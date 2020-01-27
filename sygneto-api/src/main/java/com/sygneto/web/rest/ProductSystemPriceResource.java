package com.sygneto.web.rest;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.ProductMrp;
import com.sygneto.domain.ProductSystemPrice;
import com.sygneto.service.ProductSystemPriceService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class ProductSystemPriceResource {
	 
    private final Logger log = LoggerFactory.getLogger(ProductSystemPriceResource.class);
  

    private static final String ENTITY_NAME = "productSystemPrice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductSystemPriceService productSystemPriceService;

    public ProductSystemPriceResource(ProductSystemPriceService productSystemPriceService) {
        this.productSystemPriceService = productSystemPriceService;
    }

    @Autowired
	CustomResponse customResponse;
    /**
     * {@code POST  /product-system-prices} : Create a new productSystemPrice.
     *
     * @param productSystemPrice the productSystemPrice to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productSystemPrice, or with status {@code 400 (Bad Request)} if the productSystemPrice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/productSystemPrice")
    public Object createProductSystemPrice(@RequestBody ProductSystemPrice productSystemPrice) throws URISyntaxException {
    	if (productSystemPrice.getProductSystemPriceId() != null) {
            throw new BadRequestAlertException("A new productSystemPrice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductSystemPrice result = productSystemPriceService.save(productSystemPrice);
		return customResponse.success("product System price created", result);
        
    }

    /**
     * {@code PUT  /product-system-prices} : Updates an existing productSystemPrice.
     *
     * @param productSystemPrice the productSystemPrice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productSystemPrice,
     * or with status {@code 400 (Bad Request)} if the productSystemPrice is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productSystemPrice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/productSystemPrice")
    public Object updateProductSystemPrice(@RequestBody ProductSystemPrice productSystemPrice) throws URISyntaxException {
        log.debug("REST request to update ProductSystemPrice : {}", productSystemPrice);
        if (productSystemPrice.getProductSystemPriceId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductSystemPrice result = productSystemPriceService.save(productSystemPrice);
		return customResponse.success("product System price updated", result);
          
    }

    /**
     * {@code GET  /product-system-prices} : get all the productSystemPrices.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productSystemPrices in body.
     */
    @GetMapping("/productSystemPrice")
    public Object getAllProductSystemPrices() {
    	 List<ProductSystemPrice> result= productSystemPriceService.findAll();
         if(result !=null && !result.isEmpty()) {
         	Object obj=customResponse.success("product System price found", result);
         	return obj;
         }else {
         	Object obj=customResponse.failure(404, "product System price Not found");
         	return obj;
         }
    }

    /**
     * {@code GET  /product-system-prices/:id} : get the "id" productSystemPrice.
     *
     * @param id the id of the productSystemPrice to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productSystemPrice, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/productSystemPrice/{id}")
    public Object getProductSystemPrice(@PathVariable Long id) {
        Optional<ProductSystemPrice> result = productSystemPriceService.findById(id);
        if(result !=null && result.isPresent()) {
        	Object obj=customResponse.success("product System price found", result);
        	return obj;
        }else 
        {
        	Object obj=customResponse.failure(404, "product System price Not found");
        	return obj;
        }
    }
    /**
     * {@code DELETE  /product-system-prices/:id} : delete the "id" productSystemPrice.
     *
     * @param id the id of the productSystemPrice to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/productSystemPrice/{id}")
    public Object deleteProductSystemPrice(@PathVariable Long id) {
		Optional<ProductSystemPrice> result = productSystemPriceService.findOne(id);

        if (result != null && result.isPresent()) {
			if (id != null) {
		        productSystemPriceService.delete(id);
				return customResponse.success("product System price deleted", null);

			} else {
				return customResponse.failure(404, "product System price not found");

			}
		} else {
			return customResponse.failure(404, "product System price not deleted");

		}        }
}

 